package pers.wayease.duolaimall.product.initialization;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.wayease.duolaimall.common.aop.annotation.DistributedLockedInitialization;
import pers.wayease.duolaimall.common.initialization.InitializationStrategy;
import pers.wayease.duolaimall.product.constant.RedisConstant;
import pers.wayease.duolaimall.product.service.SkuService;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.initialization
 * @name SKU bloom filter initialization strategy class, concrete strategy in batch strategy pattern.
 * @description SKU bloom filter initializer class.
 * @since 2024-10-11 11:07
 */
@Component
@Slf4j
public class SkuBloomFilterInitializationStrategy implements InitializationStrategy {

    @Autowired
    private SkuService skuService;

    @Autowired
    private RedissonClient redissonClient;

    private final long EXPRCTED_INSERTIONS = 10000;
    private final double FALSE_PROBABILITY = 0.01;

    @Override
    @DistributedLockedInitialization(distributedLockName = RedisConstant.SKU_BLOOM_FILTER_INITIALIZATION_LOCK)
    public void initialize() {
        // init bloom filter
        RBloomFilter<Long> rBloomFilter = redissonClient.getBloomFilter(RedisConstant.SKU_BLOOM_FILTER);
        rBloomFilter.tryInit(EXPRCTED_INSERTIONS, FALSE_PROBABILITY);

        // select all on sale SKU ID
        List<Long> allOnSaleSkuIdList = skuService.getAllOnSaleSkuIdList();

        // insert all on sale SKU IDs into bloom filter
        allOnSaleSkuIdList.forEach(rBloomFilter::add);
        log.info("SKU bloom filter initialized.");
    }
}
