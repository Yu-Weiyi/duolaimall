package pers.wayease.duolaimall.promo.service;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import pers.wayease.duolaimall.order.pojo.dto.OrderTradeDto;
import pers.wayease.duolaimall.order.pojo.param.OrderInfoParam;
import pers.wayease.duolaimall.promo.pojo.dto.SeckillGoodsDto;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.promo.service
 * @name PromoService
 * @description Promo service interface.
 * @since 2024-10-21 02:50
 */
public interface PromoService {

    void importIntoRedis();

    SeckillGoodsDto getSeckillGoodsDto(Long skuId);

    boolean checkOrder(Long skuId, String userId);

    void clearRedisCache();

    OrderTradeDto getTradeData(String userId, Long skuId);

    boolean submitOrder(OrderInfoParam orderInfo) throws MQBrokerException, RemotingException, InterruptedException, MQClientException;

    boolean reduceStock(OrderInfoParam orderInfo);

    void submitOrderInTransaction(OrderInfoParam orderInfo);

    List<SeckillGoodsDto> findAll();
}
