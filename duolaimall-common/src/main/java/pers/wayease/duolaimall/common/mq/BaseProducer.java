package pers.wayease.duolaimall.common.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.message.MessageBuilder;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.SendReceipt;
import org.springframework.beans.factory.annotation.Value;
import pers.wayease.duolaimall.common.constant.TopicConstant;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.mq
 * @name BaseProducer
 * @description Base producer class.
 * @since 2024-10-12 13:31
 */
@Slf4j
public abstract class BaseProducer {

    @Value("${rocketmq.producer.group}")
    private String producerGroup;

    // from construction
    private TopicConstant topicConstant;

    private ClientServiceProvider clientServiceProvider;

    private ClientConfiguration clientConfiguration;

    private Producer producer;

    public BaseProducer(ClientServiceProvider clientServiceProvider, ClientConfiguration clientConfiguration, TopicConstant topicConstant) {
        this.clientServiceProvider = clientServiceProvider;
        this.clientConfiguration = clientConfiguration;
        this.topicConstant = topicConstant;
    }

    @PostConstruct
    public void init() throws ClientException {
        producer = clientServiceProvider.newProducerBuilder()
                .setClientConfiguration(clientConfiguration)
                .setTopics(topicConstant.name())
                .build();
        log.info("RocketMQ producer {} {} initialized.", producerGroup, topicConstant.name());
    }

    public void sendMessageNow(TopicConstant topicConstant, String[] keys, String tag, String body) {
        sendMessage(topicConstant, keys, tag, body, null);
    }

    public void sendSimplifiedMessageNow(TopicConstant topicConstant, String body) {
        sendMessage(topicConstant, null, null, body, null);
    }

    public void sendMessageAfter(TopicConstant topicConstant, String[] keys, String tag, String body, Duration duration) {
        sendMessage(topicConstant, keys, tag, body, System.currentTimeMillis() + duration.toMillis());
    }

    public void sendSimplifiedMessageAfter(TopicConstant topicConstant, String body, Duration duration) {
        sendMessage(topicConstant, null, null, body, System.currentTimeMillis() + duration.toMillis());
    }

    public void sendMessageAt(TopicConstant topicConstant, String[] keys, String tag, String body, Long deliveryTimestamp) {
        sendMessage(topicConstant, keys, tag, body, deliveryTimestamp);
    }

    public void sendSimplifiedMessageAt(TopicConstant topicConstant, String body, Long deliveryTimestamp) {
        sendMessage(topicConstant, null, null, body, deliveryTimestamp);
    }

    protected void sendMessage(TopicConstant topicConstant, String[] keys, String tag, String body, Long deliveryTimestamp) {
        MessageBuilder messageBuilder = clientServiceProvider.newMessageBuilder()
//                .setMessageGroup(producerGroup)// FIFO mode
//              // NORMAL mode
                .setTopic(topicConstant.name())
                .setBody(body.getBytes(StandardCharsets.UTF_8));
        if (keys != null && keys.length > 0) {
            messageBuilder.setKeys(keys);
        }
        if (tag != null && !tag.isBlank()) {
            messageBuilder.setTag(tag);
        }
        if (deliveryTimestamp != null) {
            messageBuilder.setDeliveryTimestamp(deliveryTimestamp);
        }

        Message message = messageBuilder.build();

        try {
            SendReceipt sendReceipt = producer.send(message);
            log.info("RocketMQ producer {} sent message body {}.", topicConstant.name(), body);
        } catch (ClientException e) {
            log.warn("RocketMQ producer {} failed to send message {}.", topicConstant.name(), body, e);
        }
    }

    @PreDestroy
    public void destroy() throws IOException {
        if (producer != null) {
            producer.close();
            log.info("RocketMQ producer {} {} closed.", producerGroup, topicConstant.name());
        }
    }
}
