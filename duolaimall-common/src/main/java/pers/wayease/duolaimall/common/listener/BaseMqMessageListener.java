package pers.wayease.duolaimall.common.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.listener
 * @name BaseMqMessageListener
 * @description Base MQ message listener class, context in strategy pattern.
 * @since 2024-10-12 15:45
 */
@Slf4j
public class BaseMqMessageListener implements MessageListenerConcurrently {

    private Consumer<MessageExt> consumerStrategy;

    public BaseMqMessageListener(Consumer<MessageExt> consumerStrategy) {
        this.consumerStrategy = consumerStrategy;
        log.info("MQ message listener start with consumer strategy: {}.", consumerStrategy);
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        try {
            log.info("MQ message got, message ext list: {}.", list);

            // concrete consume strategy
            list.forEach(consumerStrategy);

            log.info("MQ message consumed, message ext list: {}.", list);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        } catch (Exception e) {
            log.warn("MQ message consume failed.", e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }
}
