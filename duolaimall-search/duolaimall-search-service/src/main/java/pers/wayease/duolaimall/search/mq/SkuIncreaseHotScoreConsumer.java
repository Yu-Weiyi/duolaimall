package pers.wayease.duolaimall.search.mq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.wayease.duolaimall.common.constant.TopicConstant;
import pers.wayease.duolaimall.common.listener.BaseMqMessageListener;
import pers.wayease.duolaimall.common.mq.BaseConsumer;
import pers.wayease.duolaimall.search.service.UpdateService;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.search.mq
 * @name SkuIncreaseHotScoreConsumer
 * @description SKU increase hot score consumer class.
 * @since 2024-10-12 16:58
 */
@Component
public class SkuIncreaseHotScoreConsumer {

    @Autowired
    private BaseConsumer baseConsumer;

    @Autowired
    private UpdateService updateService;

    @PostConstruct
    public void init() throws MQClientException {
        DefaultMQPushConsumer defaultMQPushConsumer = baseConsumer.getInitializedMQPushConsumer();
        defaultMQPushConsumer.subscribe(TopicConstant.SKU_INCREASE_HOT_SCORE.name(), "*");
        defaultMQPushConsumer.registerMessageListener(new BaseMqMessageListener(new Consumer<MessageExt>() {
            @Override
            public void accept(MessageExt messageExt) {
                Long skuId = Long.valueOf(new String(messageExt.getBody(), StandardCharsets.UTF_8));
                updateService.increaseHotScore(skuId);
            }
        }));
        defaultMQPushConsumer.start();
    }
}
