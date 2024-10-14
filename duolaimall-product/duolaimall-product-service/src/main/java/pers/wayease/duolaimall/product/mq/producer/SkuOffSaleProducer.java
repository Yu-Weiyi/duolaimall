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
 * @name SkuOffSaleProducer
 * @description SKU off sale producer class.
 * @since 2024-10-14 15:46
 */
@Component
@Slf4j
public class SkuOffSaleProducer extends BaseProducer {

    public SkuOffSaleProducer(ClientServiceProvider clientServiceProvider, ClientConfiguration clientConfiguration) {
        super(clientServiceProvider, clientConfiguration, TopicConstant.SKU_OFF_SALE);
    }

    public void sendSimplifiedMessageNow(String body) {
        sendSimplifiedMessageNow(TopicConstant.SKU_OFF_SALE, body);
    }
}
