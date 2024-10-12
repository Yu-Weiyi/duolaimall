package pers.wayease.duolaimall.search.initialization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.wayease.duolaimall.common.aop.annotation.DistributedLockedInitialization;
import pers.wayease.duolaimall.common.constant.RedisConstant;
import pers.wayease.duolaimall.common.initialization.InitializationStrategy;
import pers.wayease.duolaimall.search.client.ProductServiceClient;
import pers.wayease.duolaimall.search.service.UpdateService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.search.initialization
 * @name GoodsInitializationStrategy
 * @description Goods initialization strategy class, concrete strategy in batch strategy pattern.
 * @since 2024-10-11 17:35
 */
@Component
@Slf4j
public class GoodsInitializationStrategy implements InitializationStrategy {

    @Autowired
    private UpdateService updateService;

    @Autowired
    private ProductServiceClient productServiceClient;

    @Override
    @DistributedLockedInitialization(distributedLockName = RedisConstant.GOODS_ES_INITIALIZATION_LOCK)
    public void initialize() {
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        List<Long> skuIdList = productServiceClient.getAllOnSaleSkuIdList().getData();
        skuIdList.forEach(skuId -> {
            executorService.submit(() -> {
                updateService.upperGoods(skuId);
            });
        });

        executorService.shutdown();
    }
}
