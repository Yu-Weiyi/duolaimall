package pers.wayease.duolaimall.common.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.mq
 * @name BaseConsumer
 * @description Base consumer class.
 * @since 2024-10-17 10:39
 */
@Component
@Slf4j
public class BaseConsumer {

    @Value("${rocketmq.namesrv.addr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.group}")
    private String consumerGroup;

    private List<DefaultMQPushConsumer> defaultMQPushConsumerList;

    @Autowired
    private List<ConsumeStrategy> consumeStrategyList;

    @PostConstruct
    public void init() {
        for (ConsumeStrategy consumeStrategy : consumeStrategyList) {
            defaultMQPushConsumerList = new ArrayList<>();
            try {
                DefaultMQPushConsumer defaultMQPushConsumer = start(consumeStrategy);
                if (defaultMQPushConsumer != null) {
                    defaultMQPushConsumerList.add(defaultMQPushConsumer);
                }
            } catch (MQClientException e) {
                log.warn("RocketMQ consumer failed to start, name server: {}, consumer group: {}, topic name: {}, consume strategy: {}.",
                        namesrvAddr, consumerGroup, consumeStrategy.getTopicConstant().name(), consumeStrategy.toString());
            }
        }
        log.info("RocketMQ all consumers started.");
    }

    public DefaultMQPushConsumer start(ConsumeStrategy consumeStrategy) throws MQClientException {
        if (consumeStrategy.getTopicConstant() == null) {
            log.info("Default consume strategy, ignored.");
            return null;
        }
        String topicName = consumeStrategy.getTopicConstant().name();
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(consumerGroup);
        defaultMQPushConsumer.setNamesrvAddr(namesrvAddr);
        defaultMQPushConsumer.subscribe(topicName, "*");
        defaultMQPushConsumer.registerMessageListener(consumeStrategy);
        defaultMQPushConsumer.start();
        log.info("RocketMQ consumer started, name server: {}, consumer group: {}, topic name: {}, consume strategy: {}.",
                namesrvAddr, consumerGroup, topicName, consumeStrategy.toString());
        return defaultMQPushConsumer;
    }

    @PreDestroy
    public void destroy() {
        defaultMQPushConsumerList.forEach(DefaultMQPushConsumer::shutdown);
        log.info("RocketMQ all consumers shutdown safely.");
    }
}
