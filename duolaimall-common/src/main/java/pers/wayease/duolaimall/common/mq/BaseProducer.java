package pers.wayease.duolaimall.common.mq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pers.wayease.duolaimall.common.constant.TopicConstant;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.charset.StandardCharsets;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.mq
 * @name BaseProducer
 * @description Base producer class.
 * @since 2024-10-12 13:31
 */
@Component
@Slf4j
public class BaseProducer {

    @Value("${rocketmq.name-server}")
    String nameServer;
    @Value("${rocketmq.producer.group}")
    String producerGroup;

    private DefaultMQProducer defaultMQProducer;

    @PostConstruct
    public void init() {
        defaultMQProducer = new DefaultMQProducer(producerGroup);
        defaultMQProducer.setNamesrvAddr(nameServer);

        try {
            defaultMQProducer.start();
            log.info("RocketMQ producer initialized, name server: {}, producer group: {}.", nameServer, producerGroup);
        } catch (MQClientException e) {
            log.error("Error when initialize RocketMQ producer, name server: {}, producer group: {}.", nameServer, producerGroup);
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void destroy() {
        if (defaultMQProducer != null) {
            defaultMQProducer.shutdown();
            log.info("RocketMQ producer destroyed.");
        }
    }

    public Boolean sendMessage(TopicConstant topicConstant, Object messageBody) {
        return send(topicConstant.name(), messageBody, MessageType.NORMAL, 0);
    }

    public Boolean sendDelayMessage(TopicConstant topicConstant, Object messageBody, int delayLevel) {
        return send(topicConstant.name(), messageBody, MessageType.DELAY, delayLevel);
    }

    private Boolean send(String topicName, Object messageBody, MessageType messageType, int delayLevel) {
        String jsonMessage = JSON.toJSONString(messageBody);
        Message message = new Message(topicName, jsonMessage.getBytes(StandardCharsets.UTF_8));
        if (messageType == MessageType.DELAY) {
            if (delayLevel <= 0) {
                throw new RuntimeException("Delay level should be greater than 0, not " + delayLevel + ".");
            }
            message.setDelayTimeLevel(delayLevel);
        }

        try {
            SendResult sendResult = defaultMQProducer.send(message);

            if (sendResult == null || sendResult.getSendStatus() == null) {
                return false;
            }

            if (sendResult != null) {
                SendStatus sendStatus = sendResult.getSendStatus();
                if (SendStatus.SEND_OK.equals(sendStatus)) {
                    log.info("RocketMQ message sent, topic: {}, message: {}{}", topicName, jsonMessage, messageType == MessageType.DELAY ? ", delay level: " + delayLevel : "");
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            log.warn("RocketMQ failed to send message, topic: {}, message: {}{}", topicName, messageBody, messageType == MessageType.DELAY ? ", delay level: " + delayLevel : "");
            e.printStackTrace();
        }
        return false;
    }

    enum MessageType {
        NORMAL,
        DELAY
    }
}
