package pers.wayease.duolaimall.promo.controller.website;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.wayease.duolaimall.common.constant.RedisConstant;
import pers.wayease.duolaimall.common.constant.ResultCodeEnum;
import pers.wayease.duolaimall.common.context.UserContext;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.common.util.MD5Util;
import pers.wayease.duolaimall.order.pojo.dto.OrderTradeDto;
import pers.wayease.duolaimall.order.pojo.param.OrderInfoParam;
import pers.wayease.duolaimall.promo.cache.LocalCache;
import pers.wayease.duolaimall.promo.constant.LocalCacheStockStatusEnum;
import pers.wayease.duolaimall.promo.pojo.dto.SeckillGoodsDto;
import pers.wayease.duolaimall.promo.service.PromoService;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.promo.controller.website
 * @name SeckillController
 * @description Seckill controller class.
 * @since 2024-10-21 02:33
 */
@RestController
@RequestMapping("/seckill")
@Slf4j
public class SeckillController {

    @Autowired
    PromoService promoService;

    @Autowired
    RedissonClient redissonClient;

    @GetMapping("")
    public Result<List<SeckillGoodsDto>> seckill(){
        List<SeckillGoodsDto> all = promoService.findAll();
        return Result.ok(all);
    }

    @GetMapping("/{skuId}")
    public Result<SeckillGoodsDto> getSeckillInfo(@PathVariable Long skuId){
        SeckillGoodsDto seckillGoodsDto = promoService.getSeckillGoodsDto(skuId);
        return Result.ok(seckillGoodsDto);
    }

    @GetMapping("/auth/getSeckillSkuIdStr/{skuId}")
    public Result<String> getSeckillSkuIdStr(@PathVariable Long skuId){
        //先获取对应的userId
        String userId = UserContext.getStringUserId();
        SeckillGoodsDto seckillGoodsDto = promoService.getSeckillGoodsDto(skuId);
        if (seckillGoodsDto==null){
            return Result.build(null, ResultCodeEnum.ILLEGAL_REQUEST);
        }
        Long currentTime=new Date().getTime();
        Long beginTime=seckillGoodsDto.getStartTime().getTime();
        Long endTime=seckillGoodsDto.getEndTime().getTime();
        if (beginTime <= currentTime && currentTime <= endTime) {
            //生成一个对应的加密令牌
            String encrypted = MD5Util.encrypt(skuId + userId);
            return Result.ok(encrypted);
        }
        return Result.build(null, ResultCodeEnum.ILLEGAL_REQUEST);

    }

    @GetMapping("/auth/trade/{skuId}")
    public Result trade(@PathVariable Long skuId, @RequestParam String skuIdStr){
        //这个地方的业务是
        //先判断本地内存中间是否有库存，然后判断令牌是否正确
        //然后在这里组装order信息，但不对数据库内容改变
        String userId = UserContext.getStringUserId();

        Integer hasStock = (Integer) LocalCache.get(String.valueOf(skuId));
        if (hasStock != null && LocalCacheStockStatusEnum.NO_STOCK.getCode() == hasStock){
            return Result.build(null, ResultCodeEnum.SECKILL_FAIL);
        }
        String encrypted = MD5Util.encrypt(skuId + userId);
        if (!Objects.equals(skuIdStr, encrypted)){
            return Result.build(null, ResultCodeEnum.SECKILL_ILLEGAL);
        }
        //调用service获取信息
        OrderTradeDto tradeData = promoService.getTradeData(userId, skuId);
        return Result.ok(tradeData);
    }

    @PostMapping("/auth/submitOrder")
    public Result submitOrder(@RequestBody OrderInfoParam orderInfoParam){
        //这个地方的业务逻辑是什么呢？
        //这个地方的业务逻辑是，，先用本地缓存校验库存，，，
        //然后，，，使用redis校验是否已经抢购过了，然后打上抢购标记
        //然后。调用service扣减库存，远程调用order生成订单即可
        String userId = UserContext.getStringUserId();
        Long skuId = orderInfoParam.getOrderDetailList().get(0).getSkuId();
        Integer hasStock = (Integer) LocalCache.get(String.valueOf(skuId));
        if (hasStock != null && LocalCacheStockStatusEnum.NO_STOCK.getCode() == hasStock){
            return Result.build(null, ResultCodeEnum.SECKILL_FAIL);
        }
        //把userId存起来
        orderInfoParam.setUserId(Long.valueOf(userId));
        //然后校验redis
        String key= RedisConstant.PROMO_USER_ORDERED_FLAG + ":" + skuId + ":" + userId;
        RBucket<Object> bucket = redissonClient.getBucket(key);
        boolean existBuy = bucket.trySet(skuId);
        if(!existBuy){
            return Result.build(null, ResultCodeEnum.SECKILL_DUPLICATE_TRADE);
        }
        //不然就调用service的内容下单
        try{
            boolean result = promoService.submitOrder(orderInfoParam);
            if (!result){
                bucket.delete();
                return Result.build(null, ResultCodeEnum.SECKILL_FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            bucket.delete();
            return Result.build(null, ResultCodeEnum.SECKILL_FAIL);
        }

        return Result.ok();
    }

    @GetMapping("/auth/checkOrder/{skuId}")
    public Result checkOrder(@PathVariable Long skuId) {
        String userId = UserContext.getStringUserId();
        boolean result = promoService.checkOrder(skuId, userId);
        if (result){
            return Result.build(null, ResultCodeEnum.SECKILL_SUCCESS);
        }

        return Result.build(null, ResultCodeEnum.SECKILL_RUN);
    }

    // TODO TEMP
    @GetMapping("/temp/init")
    public Result  init(){
        log.info("备份开始");
        promoService.importIntoRedis();
        log.info("备份结束");
        return Result.ok();
    }

    // TODO TEMP
    @GetMapping("/temp/clear")
    public Result clear(){
        log.info("促销结束，开始清除数据");
        promoService.clearRedisCache();
        log.info("促销结束，完成清除数据");
        return Result.ok();
    }
}
