package pers.wayease.duolaimall.common.mq;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import pers.wayease.duolaimall.common.constant.TopicConstant;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.mq
 * @name ConsumeStrategy
 * @description Consume strategy interface.
 * @since 2024-10-17 10:42
 */
public interface ConsumeStrategy extends MessageListenerConcurrently {

    TopicConstant getTopicConstant();

    ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgList, ConsumeConcurrentlyContext context);
}
