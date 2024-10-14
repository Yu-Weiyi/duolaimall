package pers.wayease.duolaimall.common.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import pers.wayease.duolaimall.common.constant.TopicConstant;
import pers.wayease.duolaimall.common.listener.BaseMqMessageListener;

import java.util.function.Consumer;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @since 2024-10-14 03:36
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.mq
 * @name BaseConsumer
 * @description Base consumer class.
 */
@Slf4j
public abstract class BaseConsumer {

    @Value("${rocketmq.name-server}")
    String nameServer;
    @Value("${rocketmq.consumer.group}")
    String consumerGroup;

    public void init(TopicConstant topicConstant, Consumer<MessageExt> messageExtConsumer) throws MQClientException {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(consumerGroup);
        defaultMQPushConsumer.setNamesrvAddr(nameServer);
        defaultMQPushConsumer.subscribe(topicConstant.name(), "*");
        defaultMQPushConsumer.registerMessageListener(new BaseMqMessageListener(messageExtConsumer));
        defaultMQPushConsumer.start();
        log.info("Consumer {} started.", topicConstant.name());
    }

//    @PreDestroy
//    public void destroy() {
//        defaultMQPushConsumer.shutdown();
//        log.info("Consumer {} shutdown.", defaultMQPushConsumer.getSubscription());
//    }
}
