package pers.wayease.duolaimall.search.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.springframework.stereotype.Component;
import pers.wayease.duolaimall.common.constant.TopicConstant;
import pers.wayease.duolaimall.common.mq.BaseConsumer;
import pers.wayease.duolaimall.search.service.UpdateService;

import java.util.function.Consumer;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.search.mq
 * @name SkuOnSaleConsumer
 * @description SKU on sale consumer class.
 * @since 2024-10-12 15:30
 */
@Component
@Slf4j
public class SkuOnSaleConsumer extends BaseConsumer {

    private UpdateService updateService;

    public SkuOnSaleConsumer(ClientServiceProvider clientServiceProvider, ClientConfiguration clientConfiguration, UpdateService updateService) {
        super(clientServiceProvider, clientConfiguration, TopicConstant.SKU_ON_SALE, new Consumer<String>() {
            @Override
            public void accept(String skuId) {
                log.info("RocketMQ SKU on sale consumer got sku ID {}", skuId);
                updateService.upperGoods(Long.valueOf(skuId));
            }
        });
    }
}