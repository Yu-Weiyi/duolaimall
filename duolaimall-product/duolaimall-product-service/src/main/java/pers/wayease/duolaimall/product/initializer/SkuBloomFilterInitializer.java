package pers.wayease.duolaimall.product.initializer;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.wayease.duolaimall.product.constant.RedisConstant;
import pers.wayease.duolaimall.product.mapper.SkuInfoMapper;
import pers.wayease.duolaimall.product.pojo.model.SkuInfo;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.initializer
 * @name SkuBloomFilterInitializer
 * @description SKU bloom filter initializer class.
 * @since 2024-10-11 11:07
 */
@Component
@Slf4j
public class SkuBloomFilterInitializer {

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private RedissonClient redissonClient;

    private final long EXPRCTED_INSERTIONS = 10000;
    private final double FALSE_PROBABILITY = 0.01;

    public void initialize() {
        RLock rLock = redissonClient.getLock(RedisConstant.SKU_BLOOM_FILTER_INITIALIZER_LOCK);

        try {
            if (rLock.tryLock(10, 30, TimeUnit.SECONDS)) {
                try {
                    log.info("Lock on {}", RedisConstant.SKU_BLOOM_FILTER_INITIALIZER_LOCK);

                    RBloomFilter<Long> rBloomFilter = redissonClient.getBloomFilter(RedisConstant.SKU_BLOOM_FILTER);
                    rBloomFilter.tryInit(EXPRCTED_INSERTIONS, FALSE_PROBABILITY);

                    LambdaQueryWrapper<SkuInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                    lambdaQueryWrapper
                            .eq(SkuInfo::getIsSale, 1);
                    List<SkuInfo> allOnSaleSkuInfoList = skuInfoMapper.selectList(lambdaQueryWrapper);
                    List<Long> allOnSaleSkuIdList = allOnSaleSkuInfoList.stream()
                            .map(SkuInfo::getId)
                            .toList();

                    allOnSaleSkuIdList.forEach(rBloomFilter::add);
                    log.info("SKU bloom filter initialized.");
                } finally {
                    rLock.unlock();
                    log.info("Lock off {}", RedisConstant.SKU_BLOOM_FILTER_INITIALIZER_LOCK);
                }
            } else {
                log.warn("Lock {} unavailable, initialization skipped.", RedisConstant.SKU_BLOOM_FILTER_INITIALIZER_LOCK);
            }
        } catch (InterruptedException e) {
            log.error("Lock {} error, initialization skipped.", RedisConstant.SKU_BLOOM_FILTER_INITIALIZER_LOCK);
        }
    }
}
