package pers.wayease.duolaimall.cart.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.wayease.duolaimall.cart.client.ProductServiceClient;
import pers.wayease.duolaimall.cart.converter.SkuInfoConverter;
import pers.wayease.duolaimall.cart.pojo.dto.CartInfoDto;
import pers.wayease.duolaimall.cart.service.CartService;
import pers.wayease.duolaimall.common.constant.RedisConstant;
import pers.wayease.duolaimall.common.constant.ResultCodeEnum;
import pers.wayease.duolaimall.common.context.UserContext;
import pers.wayease.duolaimall.common.exception.BaseException;
import pers.wayease.duolaimall.product.pojo.dto.SkuInfoDto;

import java.util.*;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.cart.service.impl
 * @name CartServiceImpl
 * @description Cart service implement class.
 * @since 2024-10-14 20:17
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private SkuInfoConverter skuInfoConverter;

    @Autowired
    private ProductServiceClient productServiceClient;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void addToCart(Long skuId, Integer skuNum) {
        RMap<Long, CartInfoDto> rMap = redissonClient.getMap(RedisConstant.ORDER_TRADE_CODE + ":" + UserContext.getCartUserId());
        CartInfoDto cartInfoDto = rMap.get(skuId);
        if (cartInfoDto != null) {
            cartInfoDto.setSkuNum(cartInfoDto.getSkuNum() + skuNum);
            cartInfoDto.setUpdateTime(new Date());
            rMap.put(skuId, cartInfoDto);
        } else {
            SkuInfoDto skuInfoDto = productServiceClient.getSkuInfo(skuId).getData();
            CartInfoDto createCartInfoDto = skuInfoConverter.skuInfoDto2CartInfoDto(skuInfoDto, skuNum, skuId, UserContext.getCartUserId());
            rMap.put(skuId, createCartInfoDto);
        }
    }

    @Override
    public List<CartInfoDto> getCartList() {
        RMap<Long, CartInfoDto> userCartInfoDtoRMap = redissonClient.getMap(RedisConstant.ORDER_TRADE_CODE + ":" + UserContext.getStringUserId());
        RMap<Long, CartInfoDto> userTempCartInfoDtoRMap = redissonClient.getMap(RedisConstant.ORDER_TRADE_CODE + ":" + UserContext.getUserTempId());
        List<CartInfoDto> userTempCartInfoDtoList = userTempCartInfoDtoRMap.readAllValues()
                .stream()
                .toList();
        if (!UserContext.isLogined()) {
            // is not login
            return userTempCartInfoDtoList;
        }
        // is login
        List<CartInfoDto> userCartInfoDtoList = userCartInfoDtoRMap.readAllValues()
                .stream()
                .toList();
        if (userTempCartInfoDtoRMap.isEmpty()) {
            // temp is empty
            return userCartInfoDtoList;
        }
        // temp is not empty
        List<CartInfoDto> mergedCartInfoDtoList = mergeCart(userCartInfoDtoList,userTempCartInfoDtoList, userCartInfoDtoRMap);
        userTempCartInfoDtoRMap.delete();
        return mergedCartInfoDtoList;
    }

    private List<CartInfoDto> mergeCart(List<CartInfoDto> userCartInfoDtoList, List<CartInfoDto> userTempCartInfoDtoList, RMap<Long, CartInfoDto> userCartInfoDtoRMap) {
        Map<Long, CartInfoDto> resultMap = new HashMap<>();
        userCartInfoDtoList.forEach(userCartInfoDto -> {
            CartInfoDto cartInfoDto = resultMap.get(userCartInfoDto.getSkuId());
            if (cartInfoDto != null) {
                cartInfoDto.setSkuNum(cartInfoDto.getSkuNum() + userCartInfoDto.getSkuNum());
            } else {
                resultMap.put(userCartInfoDto.getSkuId(), userCartInfoDto);
            }
        });

        userTempCartInfoDtoList.forEach(userTempCartInfoDto -> {
            CartInfoDto cartInfoDto = resultMap.get(userTempCartInfoDto.getSkuId());
            if (cartInfoDto != null) {
                cartInfoDto.setSkuNum(cartInfoDto.getSkuNum() + userTempCartInfoDto.getSkuNum());
            } else {
                resultMap.put(userTempCartInfoDto.getSkuId(), userTempCartInfoDto);
            }
        });

        userCartInfoDtoRMap.putAll(resultMap);
        return new ArrayList<>(resultMap.values());
    }

    @Override
    public void checkCart( Integer isChecked, Long skuId) {
        RMap<Long, CartInfoDto> userCartInfoDtoRMap = redissonClient.getMap(RedisConstant.ORDER_TRADE_CODE + ":" + UserContext.getCartUserId());
        CartInfoDto cartInfoDto = userCartInfoDtoRMap.get(skuId);
        if (cartInfoDto != null) {
            cartInfoDto.setIsChecked(isChecked);
            userCartInfoDtoRMap.put(skuId, cartInfoDto);
        } else {
            throw new BaseException(ResultCodeEnum.ILLEGAL_REQUEST);
        }
    }

    @Override
    public void deleteCart(Long skuId) {
        RMap<Long, CartInfoDto> userCartInfoDtoRMap = redissonClient.getMap(RedisConstant.ORDER_TRADE_CODE + ":" + UserContext.getCartUserId());
        userCartInfoDtoRMap.remove(skuId);
    }

    @Override
    public void deleteChecked() {
        RMap<Long, CartInfoDto> userCartInfoDtoRMap = redissonClient.getMap(RedisConstant.ORDER_TRADE_CODE + ":" + UserContext.getCartUserId());
        List<Long> checkedIdList = new ArrayList<>();
        userCartInfoDtoRMap.forEach((skuId, userCartInfoDto) -> {
            if (userCartInfoDto.getIsChecked() == 1) {
                checkedIdList.add(skuId);
            }
        });
        checkedIdList.forEach(userCartInfoDtoRMap::remove);
    }

    @Override
    public List<CartInfoDto> getCheckedCartList() {
        RMap<Long, CartInfoDto> userCartInfoDtoRMap = redissonClient.getMap(RedisConstant.ORDER_TRADE_CODE + ":" + UserContext.getCartUserId());
        List<CartInfoDto> result = new ArrayList<>();
        userCartInfoDtoRMap.forEach((skuId, userCartInfoDto) -> {
            if (userCartInfoDto.getIsChecked() == 1) {
                result.add(userCartInfoDto);
            }
        });
        return result;
    }

    @Override
    public void delete(List<Long> skuIdList) {
        RMap<Long, CartInfoDto> userCartInfoDtoRMap = redissonClient.getMap(RedisConstant.ORDER_TRADE_CODE + ":" + UserContext.getCartUserId());
        skuIdList.forEach(userCartInfoDtoRMap::remove);
    }

    @Override
    public void refreshCartPrice(Long skuId) {
        SkuInfoDto skuInfoDto = productServiceClient.getSkuInfo(skuId).getData();
        RMap<Long, CartInfoDto> userCartInfoDtoRMap = redissonClient.getMap(RedisConstant.ORDER_TRADE_CODE + ":" + UserContext.getCartUserId());
        CartInfoDto cartInfoDto = userCartInfoDtoRMap.get(skuId);
        cartInfoDto.setCartPrice(skuInfoDto.getPrice());
        userCartInfoDtoRMap.put(skuId, cartInfoDto);
    }
}
