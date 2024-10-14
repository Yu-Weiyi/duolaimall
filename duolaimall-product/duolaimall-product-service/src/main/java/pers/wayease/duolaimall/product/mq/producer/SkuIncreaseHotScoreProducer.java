package pers.wayease.duolaimall.product.mq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.springframework.stereotype.Component;
import pers.wayease.duolaimall.common.constant.TopicConstant;
import pers.wayease.duolaimall.common.mq.BaseProducer;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.mq.producer
 * @name SkuIncreaseHotScoreProducer
 * @description SKU increase hot score producer class.
 * @since 2024-10-14 14:21
 */
@Component
@Slf4j
public class SkuIncreaseHotScoreProducer extends BaseProducer {

    public SkuIncreaseHotScoreProducer(ClientServiceProvider clientServiceProvider, ClientConfiguration clientConfiguration) {
        super(clientServiceProvider, clientConfiguration, TopicConstant.SKU_INCREASE_HOT_SCORE);
    }

    public void sendSimplifiedMessageNow(String body) {
        sendSimplifiedMessageNow(TopicConstant.SKU_INCREASE_HOT_SCORE, body);
    }
}
