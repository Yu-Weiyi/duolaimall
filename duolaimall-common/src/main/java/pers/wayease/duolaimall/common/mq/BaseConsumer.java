package pers.wayease.duolaimall.common.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.mq
 * @name BaseConsumer
 * @description Base consumer class.
 * @since 2024-10-12 15:02
 */
@Component
@Scope("prototype")
@Slf4j
public class BaseConsumer {

    @Value("${rocketmq.name-server}")
    String nameServer;
    @Value("${rocketmq.consumer.group}")
    String consumerGroup;

    private DefaultMQPushConsumer defaultMQPushConsumer;

    @PostConstruct
    public void init() {
        defaultMQPushConsumer = new DefaultMQPushConsumer(consumerGroup);
        defaultMQPushConsumer.setNamesrvAddr(nameServer);
    }

    @PreDestroy
    public void destroy() {
        if (defaultMQPushConsumer != null) {
            defaultMQPushConsumer.shutdown();
            log.info("RocketMQ consumer destroyed.");
        }
    }

    public DefaultMQPushConsumer getInitializedMQPushConsumer() {
        return defaultMQPushConsumer;
    }
}
