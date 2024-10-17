package pers.wayease.duolaimall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.wayease.duolaimall.cart.pojo.dto.CartInfoDto;
import pers.wayease.duolaimall.common.constant.ResultCodeEnum;
import pers.wayease.duolaimall.common.context.UserContext;
import pers.wayease.duolaimall.common.exception.BaseException;
import pers.wayease.duolaimall.common.util.DateUtil;
import pers.wayease.duolaimall.order.client.CartServiceClient;
import pers.wayease.duolaimall.order.client.ProductServiceClient;
import pers.wayease.duolaimall.order.client.UserServiceClient;
import pers.wayease.duolaimall.order.client.WareServiceClient;
import pers.wayease.duolaimall.order.converter.CartInfoConverter;
import pers.wayease.duolaimall.order.converter.OrderDetailConverter;
import pers.wayease.duolaimall.order.converter.OrderInfoConverter;
import pers.wayease.duolaimall.order.mapper.OrderDetailMapper;
import pers.wayease.duolaimall.order.mapper.OrderInfoMapper;
import pers.wayease.duolaimall.order.pojo.constant.OrderStatusEnum;
import pers.wayease.duolaimall.order.pojo.dto.OrderDetailDto;
import pers.wayease.duolaimall.order.pojo.dto.OrderInfoDto;
import pers.wayease.duolaimall.order.pojo.dto.OrderTradeDto;
import pers.wayease.duolaimall.order.pojo.model.OrderDetail;
import pers.wayease.duolaimall.order.pojo.model.OrderInfo;
import pers.wayease.duolaimall.order.pojo.param.OrderDetailParam;
import pers.wayease.duolaimall.order.pojo.param.OrderInfoParam;
import pers.wayease.duolaimall.order.service.OrderService;
import pers.wayease.duolaimall.user.pojo.dto.UserAddressDto;
import pers.wayease.duolaimall.ware.pojo.constant.TaskStatusEnum;
import pers.wayease.duolaimall.ware.pojo.dto.WareOrderTaskDto;
import pers.wayease.duolaimall.ware.pojo.dto.WareSkuDto;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.order.service.impl
 * @name OrderServiceImpl
 * @description Order service implement class.
 * @since 2024-10-15 22:38
 */
@Service
@Slf4j
public  class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private CartServiceClient cartServiceClient;
    @Autowired
    private WareServiceClient wareServiceClient;
    @Autowired
    private ProductServiceClient productServiceClient;

    @Autowired
    private CartInfoConverter cartInfoConverter;
    @Autowired
    private OrderInfoConverter orderInfoConverter;
    @Autowired
    private OrderDetailConverter orderDetailConverter;

//    @Autowired
//    private DelayOrderProducer delayOrderProducer;

    @Override
    public OrderTradeDto getTradeInfo() {
        // get
        List<UserAddressDto> userAddressDtoList = userServiceClient.getUserAddressListByUserId(UserContext.getUserId()).getData();
        List<CartInfoDto> checkedCartInfoDtoList = cartServiceClient.getCheckedCartList(UserContext.getStringUserId());

        // calculate
        int totalNum = 0;
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartInfoDto cartInfoDto : checkedCartInfoDtoList) {
            totalNum += cartInfoDto.getSkuNum();
            BigDecimal tempPrice = cartInfoDto.getSkuPrice().multiply(new BigDecimal(cartInfoDto.getSkuNum()));
            totalAmount = totalAmount.add(tempPrice);
        }

        // return
        OrderTradeDto orderTradeDto = new OrderTradeDto();
        orderTradeDto.setTotalNum(totalNum);
        orderTradeDto.setTotalAmount(totalAmount);
        orderTradeDto.setDetailArrayList(cartInfoConverter.cartInfoDtoList2OrderDetailDtoList(checkedCartInfoDtoList));
        orderTradeDto.setUserAddressList(userAddressDtoList);

        return orderTradeDto;
    }

    @Override
    public Long submitOrder(OrderInfoParam orderInfoParam) {
        // multi thread check
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        List<OrderDetailParam> orderDetailParamList = orderInfoParam.getOrderDetailList();
        List<CompletableFuture<Void>> completeableFutureList = orderDetailParamList.stream()
                .map(orderDetailParam -> {
                    return CompletableFuture.runAsync(() -> {
                        // stock check
                        Boolean hasStock = wareServiceClient.hasStock(orderDetailParam.getSkuId(), orderDetailParam.getSkuNum()).getData();
                        if (!hasStock) {
                            // not enough
                            throw new BaseException(ResultCodeEnum.STOCK_NOT_ENOUGH);
                        }

                        // price check
                        BigDecimal skuPrice = productServiceClient.getSkuPrice(orderDetailParam.getSkuId()).getData();
                        if (skuPrice == null || !skuPrice.equals(orderDetailParam.getOrderPrice())) {
                            // price has changed
                            cartServiceClient.refreshCartPrice(UserContext.getStringUserId(), orderDetailParam.getSkuId());
                            throw new BaseException(ResultCodeEnum.PRICE_HAS_CHANGED);
                        }
                    }, executorService);
                })
                .toList();
        CompletableFuture<Void> allOf = CompletableFuture.allOf(completeableFutureList.toArray(new CompletableFuture[0]));
        try {
            allOf.get();
        } catch (ExecutionException e) {
            // check failed
            log.warn("Check failed, reject the request.", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executorService.shutdown();
        }

        // save
        OrderInfo orderInfo = orderInfoConverter.orderInfoParam2Po(orderInfoParam);
        orderInfo.setUserId(UserContext.getUserId());
        Long orderId = saveOrderInfo(orderInfo);

        // clear
        List<Long> skuIdList = orderInfo.getOrderDetailList().stream()
                .map(OrderDetail::getSkuId)
                .toList();
        cartServiceClient.removeCartProductsInOrder(UserContext.getStringUserId(), skuIdList);

        // set delay callback
        // FIXME delayOrderProducer
//        delayOrderProducer.sendSimplifiedMessageAfter(String.valueOf(orderId), Duration.ofMinutes(30L));
        return orderId;
    }

    @Override
    @Transactional
    public Long saveOrderInfo(OrderInfo orderInfo) {
        orderInfo.setOrderStatus(OrderStatusEnum.UNPAID.name());
        orderInfo.sumTotalAmount();
        orderInfo.setOutTradeNo("NO" + IdWorker.getId());
        orderInfo.setTradeBody(orderInfo.getOrderDetailList().get(0).getSkuName());
        Date expireTime = DateUtil.datePlusSeconds(new Date(), 60 * 30);// 0.5 h
        orderInfo.setExpireTime(expireTime);

        int orderInfoUpdateRows = orderInfoMapper.insert(orderInfo);
        if (orderInfoUpdateRows < 1) {
            // fail to save order info
            log.warn("Save order info failed, orderId:{}", orderInfo.getId());
            throw new BaseException(ResultCodeEnum.FAIL);
        }
        log.info("Save order info success, orderId:{}", orderInfo.getId());

        Long orderId = orderInfo.getId();
        for (OrderDetail orderDetail : orderInfo.getOrderDetailList()) {
            orderDetail.setOrderId(orderId);
            int orderDetailUpdateRows = orderDetailMapper.insert(orderDetail);
            if (orderDetailUpdateRows < 1) {
                // fail to save order detail
                log.warn("Save order detail failed, orderId:{}", orderId);
                throw new BaseException(ResultCodeEnum.FAIL);
            }
            log.info("Save order detail success, orderId:{}", orderId);

        }
        return orderId;
    }

    @Override
    public IPage<OrderInfoDto> getPage(Page<OrderInfo> pageParam) {
        LambdaQueryWrapper<OrderInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .isNull(OrderInfo::getParentOrderId)
                .eq(OrderInfo::getUserId, UserContext.getUserId());
        orderInfoMapper.selectPage(pageParam, lambdaQueryWrapper);
        List<OrderInfo> orderInfoList = pageParam.getRecords();
        List<OrderInfoDto> orderInfoDtoList = orderInfoConverter.orderInfoPoList2DtoList(orderInfoList);

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        List<CompletableFuture<OrderInfoDto>> completableFutureList = orderInfoDtoList.stream()
                .map(orderInfoDto -> {
                    return CompletableFuture.supplyAsync(() -> {
                        Map<String, Object> searchMap = new HashMap<>();
                        searchMap.put("order_id", orderInfoDto.getId());
                        List<OrderDetail> orderDetailList = orderDetailMapper.selectByMap(searchMap);
                        orderInfoDto.setOrderDetailList(orderDetailConverter.orderDetailPoList2DtoList(orderDetailList));
                        orderInfoDto.setOrderStatusName(OrderStatusEnum.getStatusDescribeByStatus(orderInfoDto.getOrderStatus()));
                        return orderInfoDto;
                    }, executorService);
                })
                .toList();
        CompletableFuture<Void> allOf = CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]));
        try {
            allOf.get();
        } catch (ExecutionException e) {
            // check failed
            log.warn("Operation failed, reject the request.", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executorService.shutdown();
        }

        Page<OrderInfoDto> orderInfoDtoPage = cartInfoConverter.orderInfoDtoList2DtoPage(pageParam, orderInfoDtoList);
        return orderInfoDtoPage;
    }

    @Override
    public OrderInfoDto getOrderInfo(Long orderId) {
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        OrderInfoDto orderInfoDto = orderInfoConverter.orderInfoPo2Dto(orderInfo);
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("order_id", orderId);
        List<OrderDetail> orderDetailList = orderDetailMapper.selectByMap(searchMap);
        List<OrderDetailDto> orderDetailDtoList = orderDetailConverter.orderDetailPoList2DtoList(orderDetailList);
        orderInfoDto.setOrderDetailList(orderDetailDtoList);
        return orderInfoDto;
    }

    @Override
    public void successPay(Long orderId) {
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);

        orderInfo.setOrderStatus(OrderStatusEnum.PAID.name());
        int updateRows = orderInfoMapper.updateById(orderInfo);
        if (updateRows < 1) {
            // failed to update order status
            throw new BaseException(ResultCodeEnum.FAIL);
        }

        Boolean isSuccess =  ResultCodeEnum.SUCCESS.equals(wareServiceClient.decreaseStock(orderId).getCode());
        if (!isSuccess) {
            // failed to decrease stock
            throw new BaseException(ResultCodeEnum.FAIL);
        }
        // success
    }

    @Override
    public List<WareOrderTaskDto> orderSplit(String orderId, List<WareSkuDto> wareSkuDTOList) {
        OrderInfoDto orderInfoDto = getOrderInfo(Long.valueOf(orderId));
        if (orderInfoDto == null) {
            return new ArrayList<>();
        }

        List<OrderDetailDto> orderDetailDtoList = orderInfoDto.getOrderDetailList();
        List<WareOrderTaskDto> wareOrderTaskDtoList = wareSkuDTOList.stream()
                .map(wareSkuDto -> {
                    String wareId = wareSkuDto.getWareId();
                    List<String> skuIdList = wareSkuDto.getSkuIds();

                    List<OrderDetail> subOrderDetailList = new ArrayList<>();
                    for (OrderDetailDto orderDetailDto : orderDetailDtoList) {
                        if (skuIdList.contains(orderDetailDto.getSkuId())) {
                            OrderDetail subOrderDetail = orderDetailConverter.orderDetailDto2Po(orderDetailDto);
                            subOrderDetail.setId(null);
                            subOrderDetailList.add(subOrderDetail);
                        }
                    }

                    if (!subOrderDetailList.isEmpty()) {
                        OrderInfo subOrderInfo = orderInfoConverter.orderInfoDto2Po(orderInfoDto);
                        subOrderInfo.setParentOrderId(Long.valueOf(orderId));
                        subOrderInfo.setUpdateTime(new Date());
                        subOrderInfo.setOrderDetailList(subOrderDetailList);
                        subOrderInfo.setOutTradeNo("NO" + IdWorker.getId());
                        subOrderInfo.setId(null);
                        subOrderInfo.sumTotalAmount();
                        subOrderInfo.setTradeBody(subOrderDetailList.get(0).getSkuName());

                        saveOrderInfo(subOrderInfo);
                        subOrderInfo.setWareId(wareId);
                        return orderInfoConverter.orderInfoPo2WareOrderTaskDto(subOrderInfo);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();

        // update original order info
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderStatus(OrderStatusEnum.SPLIT.name());
        orderInfo.setId(orderInfoDto.getId());
        int updateRows = orderInfoMapper.updateById(orderInfo);
        if (updateRows < 1) {
            // failed to update original order info
            throw new BaseException(ResultCodeEnum.FAIL);
        }

        return wareOrderTaskDtoList;
    }

    @Override
    public void successLockStock(String orderId, String taskStatus) {
        String orderStatus = TaskStatusEnum.DEDUCTED.name().equals(taskStatus) ? OrderStatusEnum.WAIT_DELEVER.name() : OrderStatusEnum.STOCK_EXCEPTION.name();

        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        orderInfo.setOrderStatus(orderStatus);
        int updateRows = orderInfoMapper.updateById(orderInfo);
        if (updateRows < 1) {
            // failed to update order info
            throw new BaseException(ResultCodeEnum.FAIL);
        }
    }
}