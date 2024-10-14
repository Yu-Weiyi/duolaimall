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
 * @name SkuIncreaseHotScoreConsumer
 * @description SKU increase hot score consumer class.
 * @since 2024-10-12 16:58
 */
@Component
@Slf4j
public class SkuIncreaseHotScoreConsumer extends BaseConsumer {

    private UpdateService updateService;

    public SkuIncreaseHotScoreConsumer(ClientServiceProvider clientServiceProvider, ClientConfiguration clientConfiguration, UpdateService updateService) {
        super(clientServiceProvider, clientConfiguration, TopicConstant.SKU_INCREASE_HOT_SCORE, new Consumer<String>() {
            @Override
            public void accept(String skuId) {
                log.info("RocketMQ SKU increase hot score consumer got sku ID {}.", skuId);
                updateService.increaseHotScore(Long.valueOf(skuId));
            }
        });
    }
}