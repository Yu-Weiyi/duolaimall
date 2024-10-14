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
 * @name SkuOnSaleProducer
 * @description SKU on sale producer class.
 * @since 2024-10-14 15:54
 */
@Component
@Slf4j
public class SkuOnSaleProducer extends BaseProducer {

    public SkuOnSaleProducer(ClientServiceProvider clientServiceProvider, ClientConfiguration clientConfiguration) {
        super(clientServiceProvider, clientConfiguration, TopicConstant.SKU_ON_SALE);
    }

    public void sendSimplifiedMessageNow(String body) {
        sendSimplifiedMessageNow(TopicConstant.SKU_ON_SALE, body);
    }
}
