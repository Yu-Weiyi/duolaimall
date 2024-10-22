package pers.wayease.duolaimall.order.mq;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.wayease.duolaimall.common.constant.RedisConstant;
import pers.wayease.duolaimall.common.constant.TopicConstant;
import pers.wayease.duolaimall.common.mq.ConsumeStrategy;
import pers.wayease.duolaimall.order.pojo.param.OrderInfoParam;
import pers.wayease.duolaimall.order.service.OrderService;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.order.mq
 * @name PromoOrderConsumerStrategy
 * @description Promo order consumer strategy.
 * @since 2024-10-21 03:34
 */
@Component
@Slf4j
public class PromoOrderConsumerStrategy implements ConsumeStrategy {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public TopicConstant getTopicConstant() {
        return TopicConstant.SECKILL_GOODS_QUEUE_TOPIC;
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgList, ConsumeConcurrentlyContext context) {
        int count = msgList.size();
        for (MessageExt messageExt : msgList) {
            try {
                byte[] body = messageExt.getBody();
                String jsonMessage = new String(body, StandardCharsets.UTF_8);
                Object messageObject = JSON.parseObject(jsonMessage, Object.class);
                log.info("RocketMQ consumer {} listener receive message: {}.", getTopicConstant().name(), jsonMessage);

                OrderInfoParam orderInfoParam = (OrderInfoParam) messageObject;

                Long skuId = orderService.saveScekillOrder(orderInfoParam);
                if (skuId!=null){
                    RMap<String, Long> submitMap = redissonClient.getMap(RedisConstant.PROMO_SUBMIT_ORDER);

                    String key = orderInfoParam.getUserId() + "" + orderInfoParam.getOrderDetailList().get(0).getSkuId();
                    submitMap.put(key, skuId);
                }

            } catch (Exception e) {
                log.warn("RocketMQ consumer {} consumed message.", getTopicConstant());
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
        log.info("RocketMQ consumer {} consumed {} messages.", getTopicConstant(), count);
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
