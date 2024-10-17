package pers.wayease.duolaimall.search.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.wayease.duolaimall.common.constant.TopicConstant;
import pers.wayease.duolaimall.common.mq.ConsumeStrategy;
import pers.wayease.duolaimall.search.service.UpdateService;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.search.mq
 * @name SkuOnSaleConsumeStrategy
 * @description SKU on sale consume strategy class.
 * @since 2024-10-17 11:21
 */
@Component
@Slf4j
public class SkuOnSaleConsumeStrategy implements ConsumeStrategy {

    @Autowired
    private UpdateService updateService;

    @Override
    public TopicConstant getTopicConstant() {
        return TopicConstant.SKU_ON_SALE;
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgList, ConsumeConcurrentlyContext context) {
        int count = msgList.size();
        for (MessageExt messageExt : msgList) {
            try {
                byte[] body = messageExt.getBody();
                String jsonMessage = new String(body, StandardCharsets.UTF_8);
//            Object messageObject = JSON.parseObject(jsonMessage, Object.class);
                log.info("RocketMQ consumer {} listener receive message: {}.", getTopicConstant().name(), jsonMessage);

                Long skuId = Long.valueOf(jsonMessage);
                updateService.upperGoods(skuId);
            } catch (Exception e) {
                log.warn("RocketMQ consumer {} consumed message.", getTopicConstant());
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        };
        log.info("RocketMQ consumer {} consumed {} messages.", getTopicConstant(), count);
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
