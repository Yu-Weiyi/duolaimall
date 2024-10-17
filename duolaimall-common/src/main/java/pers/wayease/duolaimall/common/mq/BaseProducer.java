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
 * @since 2024-10-17 09:46
 */
@Component
@Slf4j
public class BaseProducer {

    @Value("${rocketmq.namesrv.addr}")
    private String namesrvAddr;
    @Value("${rocketmq.producer.group}")
    private String producerGroup;

    DefaultMQProducer defaultMQProducer;

    @PostConstruct
    public void init() {
        defaultMQProducer = new DefaultMQProducer(producerGroup);
        defaultMQProducer.setNamesrvAddr(namesrvAddr);

        try {
            defaultMQProducer.start();
            log.info("RocketMQ producer started, name server: {}, producer group: {}.", namesrvAddr, producerGroup);
        } catch (MQClientException e) {
            log.warn("RocketMQ producer failed to start, name server: {}, producer group: {}.", namesrvAddr, producerGroup);
        }
    }

    public Boolean sendMessage(TopicConstant topicConstant, Object messageBody) {
        String topicName = topicConstant.name();
        try {
            String jsonMessage = JSON.toJSONString(messageBody);
            log.info("RocketMQ producer ready to send normal message, topic: {}, message: {}.", topicName, jsonMessage);

            Message message = new Message(topicName, jsonMessage.getBytes(StandardCharsets.UTF_8));

            SendResult sendResult = defaultMQProducer.send(message);

            if (sendResult == null || sendResult.getSendStatus() == null) {
                log.warn("RocketMQ producer no send result after send normal message, topic: {}, message: {}.", topicName, jsonMessage);
                return false;
            }

            SendStatus sendStatus = sendResult.getSendStatus();
            if (sendStatus.equals(SendStatus.SEND_OK)) {
                log.info("RocketMQ producer sent normal message，topic: {}, message: {}", topicName, jsonMessage);
                return true;
            }else {
                log.warn("RocketMQ producer failed to send normal message, topic: {}, message: {}, status: {}.", topicName, jsonMessage, sendStatus);
                return false;
            }
        } catch (Exception e) {
            log.warn("RocketMQ producer throw exception when send normal message, topic: {}, message: {}.", topicName, messageBody, e);
        }
        return false;
    }

    public Boolean sendDelayMessage(TopicConstant topicConstant, Object messageBody, int delayLevel) {
        String topicName = topicConstant.name();
        if (delayLevel <= 0) {
            log.warn("RocketMQ producer set wrong delay level {}", delayLevel);
            return false;
        }

        try {
            String jsonMessage = JSON.toJSONString(messageBody);
            log.info("RocketMQ producer ready to send delay message, topic: {}, message: {}, delay level: {}.", topicName, jsonMessage, delayLevel);

            Message message = new Message(topicName, jsonMessage.getBytes(StandardCharsets.UTF_8));
            message.setDelayTimeLevel(delayLevel);
            SendResult sendResult = defaultMQProducer.send(message);

            if (sendResult == null || sendResult.getSendStatus() == null) {
                log.warn("RocketMQ producer no send result after send delay message, topic: {}, message: {}.", topicName, jsonMessage);
                return false;
            }

            SendStatus sendStatus = sendResult.getSendStatus();
            if (sendStatus.equals(SendStatus.SEND_OK)) {
                log.info("RocketMQ producer sent delay message，topic: {}, message: {}, delay level: {}.", topicName, jsonMessage, delayLevel);;
                return true;
            }else {
                log.warn("RocketMQ producer failed to send delay message, topic: {}, message: {}, status: {}, delay level: {}.", topicName, jsonMessage, sendStatus, sendResult.getSendStatus());
                return false;
            }
        } catch (Exception e) {
            log.warn("RocketMQ producer throw exception when send delay message, topic: {}, message: {}, delay level: {}.", topicName, messageBody, delayLevel, e);
        }
        return false;
    }

    @PreDestroy
    public void destroy() {
        defaultMQProducer.shutdown();
        log.info("RocketMQ producer shutdown safely.");
    }
}
