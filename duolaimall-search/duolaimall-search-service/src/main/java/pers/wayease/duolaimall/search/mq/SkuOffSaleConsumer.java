package pers.wayease.duolaimall.search.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pers.wayease.duolaimall.common.constant.TopicConstant;
import pers.wayease.duolaimall.common.listener.BaseMqMessageListener;
import pers.wayease.duolaimall.search.service.UpdateService;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;


@Component
@Slf4j
//public class SkuOffSaleConsumer extends BaseConsumer {
public class SkuOffSaleConsumer {

    @Value("${rocketmq.name-server}")
    String nameServer;
    @Value("${rocketmq.consumer.group}")
    String consumerGroup;

    @Autowired
    private UpdateService updateService;

    @PostConstruct
    public void init() throws MQClientException {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(consumerGroup);
        defaultMQPushConsumer.setNamesrvAddr(nameServer);
        defaultMQPushConsumer.subscribe(TopicConstant.SKU_OFF_SALE.name(), "*");
        defaultMQPushConsumer.registerMessageListener(new BaseMqMessageListener(new Consumer<MessageExt>() {
            @Override
            public void accept(MessageExt messageExt) {
                Long skuId = Long.valueOf(new String(messageExt.getBody(), StandardCharsets.UTF_8));
                updateService.lowerGoods(skuId);
            }
        }));
        defaultMQPushConsumer.start();
        log.info("Consumer {} started.", TopicConstant.SKU_OFF_SALE.name());
    }
    // TODO consumer
}