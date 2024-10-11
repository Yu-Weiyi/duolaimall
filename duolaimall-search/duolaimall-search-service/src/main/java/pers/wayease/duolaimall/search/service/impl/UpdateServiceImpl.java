package pers.wayease.duolaimall.search.service.impl;

import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.wayease.duolaimall.product.pojo.dto.CategoryHierarchyDto;
import pers.wayease.duolaimall.product.pojo.dto.PlatformAttributeInfoDto;
import pers.wayease.duolaimall.product.pojo.dto.SkuInfoDto;
import pers.wayease.duolaimall.product.pojo.dto.TrademarkDto;
import pers.wayease.duolaimall.search.client.ProductServiceClient;
import pers.wayease.duolaimall.search.constant.RedisConstant;
import pers.wayease.duolaimall.search.pojo.model.Goods;
import pers.wayease.duolaimall.search.pojo.model.SearchAttribute;
import pers.wayease.duolaimall.search.repository.GoodsRepository;
import pers.wayease.duolaimall.search.service.UpdateService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.search.service.impl
 * @name UpdateServiceImpl
 * @description Update service implement class.
 * @since 2024-10-10 11:13
 */
@Service
public class UpdateServiceImpl implements UpdateService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ProductServiceClient productServiceClient;

    @Autowired
    private RedissonClient redissonClient;

    private ExecutorService executorService = Executors.newFixedThreadPool(32);

    private static final int HOT_SCORE_INCREASE_STEP = 1;
    private static final int HOT_SCORE_UPDATE_STEP = 5;

    @Override
    public void upperGoods(Long skuId) {
        Goods goods = new Goods();
        goods.setId(skuId);

        CompletableFuture<SkuInfoDto> skuInfoCompletableFuture = CompletableFuture.supplyAsync(() -> {
            SkuInfoDto skuInfoDto = productServiceClient.getSkuInfo(skuId).getData();
            goods.setDefaultImg(skuInfoDto.getSkuDefaultImg());
            goods.setTitle(skuInfoDto.getSkuName());
            goods.setPrice(skuInfoDto.getPrice().doubleValue());
            goods.setTmId(skuInfoDto.getTmId());
            return skuInfoDto;
        }, executorService);

        CompletableFuture<Void> searchAttributeListCompletableFuture = CompletableFuture.runAsync(() -> {
            List<PlatformAttributeInfoDto> platformAttributeInfoList = productServiceClient.getPlatformAttributeInfoList(skuId).getData();
            List<SearchAttribute> searchAttributeList = platformAttributeInfoList.stream()
                    .map(platformAttributeInfoDto -> {
                        SearchAttribute searchAttribute = new SearchAttribute();
                        searchAttribute.setAttrId(platformAttributeInfoDto.getId());
                        searchAttribute.setAttrName(platformAttributeInfoDto.getAttrName());
                        searchAttribute.setAttrValue(platformAttributeInfoDto.getAttrValueList().get(0).getValueName());
                        return searchAttribute;
                    })
                    .toList();
            goods.setAttrs(searchAttributeList);
        }, executorService);

        CompletableFuture<Void> categoryHierarchyCompletableFuture = skuInfoCompletableFuture.thenAcceptAsync(skuInfo -> {
            CategoryHierarchyDto categoryHierarchyDto = productServiceClient.getCategoryHierarchy(skuInfo.getThirdLevelCategoryId()).getData();
            goods.setFirstLevelCategoryId(categoryHierarchyDto.getFirstLevelCategoryId());
            goods.setFirstLevelCategoryName(categoryHierarchyDto.getFirstLevelCategoryName());
            goods.setSecondLevelCategoryId(categoryHierarchyDto.getSecondLevelCategoryId());
            goods.setSecondLevelCategoryName(categoryHierarchyDto.getSecondLevelCategoryName());
            goods.setThirdLevelCategoryId(categoryHierarchyDto.getThirdLevelCategoryId());
            goods.setThirdLevelCategoryName(categoryHierarchyDto.getThirdLevelCategoryName());
        }, executorService);

        CompletableFuture<Void> trademarkCompletableFuture = skuInfoCompletableFuture.thenAcceptAsync(skuInfo -> {
            TrademarkDto trademarkDto = productServiceClient.getTrademarkMarkDto(skuInfo.getTmId()).getData();
            goods.setTmName(trademarkDto.getTmName());
            goods.setTmLogoUrl(trademarkDto.getLogoUrl());
        }, executorService);

        CompletableFuture.allOf(
                skuInfoCompletableFuture,
                searchAttributeListCompletableFuture,
                categoryHierarchyCompletableFuture,
                trademarkCompletableFuture
        ).join();

        goodsRepository.save(goods);
    }

    @Override
    public void lowerGoods(Long skuId) {
        goodsRepository.deleteById(skuId);
    }

    @Override
    public void increaseHotScore(Long skuId) {
        // in Redis
        RScoredSortedSet<Long> rScoredSortedSet = redissonClient.getScoredSortedSet(RedisConstant.HOT_SCORE);
        rScoredSortedSet.addScore(skuId, HOT_SCORE_INCREASE_STEP);
        Double score = rScoredSortedSet.getScore(skuId);
        // in Elasticsearch
        if (score.intValue() % HOT_SCORE_UPDATE_STEP == 0) {
            Optional<Goods> goodsOptional = goodsRepository.findById(skuId);
            Goods goods = goodsOptional.get();
            goods.setHotScore(score.longValue());
            goodsRepository.save(goods);
        }
    }
}
