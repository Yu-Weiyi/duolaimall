package pers.wayease.duolaimall.common.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.consumer.ConsumeResult;
import org.apache.rocketmq.client.apis.consumer.FilterExpression;
import org.apache.rocketmq.client.apis.consumer.FilterExpressionType;
import org.apache.rocketmq.client.apis.consumer.PushConsumer;
import org.apache.rocketmq.client.apis.message.MessageId;
import org.springframework.beans.factory.annotation.Value;
import pers.wayease.duolaimall.common.constant.TopicConstant;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
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

    @Value("${rocketmq.consumer.group}")
    private String consumerGroup;

    // from construction
    private TopicConstant topicConstant;
    private Consumer<String> consumer;

    private ClientServiceProvider clientServiceProvider;

    private ClientConfiguration clientConfiguration;

    private PushConsumer pushConsumer;

    public BaseConsumer(ClientServiceProvider clientServiceProvider, ClientConfiguration clientConfiguration, TopicConstant topicConstant, Consumer<String> consumer) {
        this.clientConfiguration = clientConfiguration;
        this.clientServiceProvider = clientServiceProvider;
        this.topicConstant = topicConstant;
    }

    @PostConstruct
    public void init() throws ClientException {
        pushConsumer = clientServiceProvider.newPushConsumerBuilder()
                .setClientConfiguration(clientConfiguration)
                .setConsumerGroup(consumerGroup)
                .setSubscriptionExpressions(Collections.singletonMap(
                        topicConstant.name(),
                        new FilterExpression("*", FilterExpressionType.TAG)
                ))
                .setMessageListener(messageView -> {
                    MessageId messageId = messageView.getMessageId();
                    String bodyString = new String(messageView.getBody().array(), StandardCharsets.UTF_8);
                    log.info("RocketMQ message, ID {}, body string {}", messageId, bodyString);
                    try {
                        consumer.accept(bodyString);
                    } catch (Exception e) {
                        log.warn("RocketMQ consumer {} {} failed to consume, message id: {}, body: {}", consumerGroup, topicConstant.name(), messageId, bodyString, e);
                        return ConsumeResult.FAILURE;
                    }
                    log.info("RocketMQ consumer {} {} consumed message id: {}, body: {}", consumerGroup, topicConstant.name(), messageId, bodyString);
                    return ConsumeResult.SUCCESS;
                })
                .build();
        log.info("RocketMQ consumer {} {} initialized.", consumerGroup, topicConstant.name());
    }

    @PreDestroy
    public void destroy() throws IOException {
        if (pushConsumer != null) {
            pushConsumer.close();
            log.info("RocketMQ consumer {} {} closed.", consumerGroup, topicConstant.name());
        }
    }
}
