package pers.wayease.duolaimall.product.service.impl;

import com.alibaba.fastjson.JSON;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pers.wayease.duolaimall.product.client.SearchServiceClient;
import pers.wayease.duolaimall.product.constant.RedisConstant;
import pers.wayease.duolaimall.product.converter.SkuInfoConverter;
import pers.wayease.duolaimall.product.mapper.SkuInfoMapper;
import pers.wayease.duolaimall.product.mapper.SpuSaleAttributeValueMapper;
import pers.wayease.duolaimall.product.pojo.dto.*;
import pers.wayease.duolaimall.product.pojo.model.SkuInfo;
import pers.wayease.duolaimall.product.service.CategoryService;
import pers.wayease.duolaimall.product.service.ProductDetailService;
import pers.wayease.duolaimall.product.service.SkuService;
import pers.wayease.duolaimall.product.service.SpuService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.service.impl
 * @name ProductDetailServiceImpl
 * @description Product detail service implement class.
 * @since 2024-10-09 17:14
 */
@Service
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    private SpuService spuService;
    @Autowired
    private SkuService skuService;
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private SkuInfoMapper skuInfoMapper;
    @Autowired
    private SpuSaleAttributeValueMapper spuSaleAttributeValueMapper;
    
    @Autowired
    private SkuInfoConverter skuInfoConverter;

    @Autowired
    private SearchServiceClient searchServiceClient;

    @Autowired
    private RedissonClient redissonClient;

    private ExecutorService executorService = Executors.newFixedThreadPool(32);

    @Override
    public ProductDetailDto getItemBySkuId(Long skuId) {
        ProductDetailDto productDetailDto = new ProductDetailDto();

        RBloomFilter<Long> rBloomFilter = redissonClient.getBloomFilter(RedisConstant.SKU_BLOOM_FILTER);
        if (!rBloomFilter.contains(skuId)) {
            // filtered
            return productDetailDto;
        }
        // passed

        searchServiceClient.incrHotScore(skuId);

        CompletableFuture<SkuInfoDto> skuInfoDtoCompletableFuture = CompletableFuture.supplyAsync(() -> {
            SkuInfo skuInfo = skuInfoMapper.selectObjectById(skuId);
            SkuInfoDto skuInfoDto = skuInfoConverter.skuInfoPo2Dto(skuInfo);
            productDetailDto.setSkuInfo(skuInfoDto);
            return skuInfoDto;
        }, executorService);

        CompletableFuture<Void> priceCompletableFuture = CompletableFuture.runAsync(() -> {
            BigDecimal skuPrice = skuService.getPrice(skuId);
            productDetailDto.setPrice(skuPrice);
        }, executorService);

        CompletableFuture<Void> categoryHierachyCompletableFuture = skuInfoDtoCompletableFuture.thenAcceptAsync(skuInfoDto -> {
            CategoryHierarchyDto categoryHierarchyDto = categoryService.getCategortHierarchyByThirdLevelCategoryId(skuInfoDto.getThirdLevelCategoryId());
            productDetailDto.setCategoryHierarchy(categoryHierarchyDto);
        }, executorService);

        CompletableFuture<Void> spuSaleAttrListCompletableFuture = skuInfoDtoCompletableFuture.thenAcceptAsync(skuInfoDto -> {
            List<SpuSaleAttributeInfoDto> spuSaleAttributeInfoDtoList = skuService.getSpuSaleAttributeInfoList(skuInfoDto.getSpuId(), skuId);
            productDetailDto.setSpuSaleAttrList(spuSaleAttributeInfoDtoList);
        }, executorService);

        CompletableFuture<Void> valuesSkuJsonCompletableFuture = skuInfoDtoCompletableFuture.thenAcceptAsync(skuInfoDto -> {
            Map<String, Long> skuValueIdsMap = spuService.getSkuSaleAttributeMap(skuInfoDto.getSpuId());
            String mapJSON = JSON.toJSONString(skuValueIdsMap);
            productDetailDto.setValuesSkuJson(mapJSON);
        }, executorService);

        CompletableFuture<Void> spuPosterListCompletableFuture = skuInfoDtoCompletableFuture.thenAcceptAsync(skuInfoDto -> {
            List<SpuPosterDto> spuPosterDtoList = spuService.getSpuPosterBySpuId(skuInfoDto.getSpuId());
            productDetailDto.setSpuPosterList(spuPosterDtoList);
        }, executorService);

        CompletableFuture<Void> skuAttrListCompletableFuture = CompletableFuture.runAsync(() -> {
            List<PlatformAttributeInfoDto> platformAttributeInfoDtoList = skuService.getPlatformAttrInfoBySkuId(skuId);
            List<SkuSpecificationDto> skuSpecificationList = platformAttributeInfoDtoList.stream().map(platformAttributeInfoDto -> {
                SkuSpecificationDto skuSpecification = new SkuSpecificationDto();
                skuSpecification.setAttrName(platformAttributeInfoDto.getAttrName());

                List<PlatformAttributeValueDto> platformAttributeValueDtoList = platformAttributeInfoDto.getAttrValueList();
                if (!CollectionUtils.isEmpty(platformAttributeValueDtoList)) {
                    String valueName = platformAttributeValueDtoList.get(0).getValueName();
                    skuSpecification.setAttrValue(valueName);
                }

                return skuSpecification;
            }).toList();
            productDetailDto.setSkuAttrList(skuSpecificationList);
        }, executorService);

        CompletableFuture.allOf(
                skuInfoDtoCompletableFuture,
                priceCompletableFuture,
                categoryHierachyCompletableFuture,
                spuSaleAttrListCompletableFuture,
                valuesSkuJsonCompletableFuture,
                spuPosterListCompletableFuture,
                skuAttrListCompletableFuture
        ).join();
        return productDetailDto;
    }
}
