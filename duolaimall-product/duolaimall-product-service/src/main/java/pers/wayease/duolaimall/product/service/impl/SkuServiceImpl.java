package pers.wayease.duolaimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.wayease.duolaimall.product.client.SearchServiceClient;
import pers.wayease.duolaimall.product.converter.PlatformAttributeInfoConverter;
import pers.wayease.duolaimall.product.converter.SkuInfoConverter;
import pers.wayease.duolaimall.product.converter.SpuInfoConverter;
import pers.wayease.duolaimall.product.mapper.*;
import pers.wayease.duolaimall.product.pojo.dto.PlatformAttributeInfoDto;
import pers.wayease.duolaimall.product.pojo.dto.SkuInfoDto;
import pers.wayease.duolaimall.product.pojo.dto.SpuSaleAttributeInfoDto;
import pers.wayease.duolaimall.product.pojo.dto.page.SkuInfoPageDto;
import pers.wayease.duolaimall.product.pojo.model.*;
import pers.wayease.duolaimall.product.pojo.param.SkuInfoParam;
import pers.wayease.duolaimall.product.service.SkuService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 0.1
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.service.impl
 * @name SkuServiceImpl
 * @description SKU service implement class.
 * @since 2024-10-09 14:35
 */
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuInfoMapper skuInfoMapper;
    @Autowired
    private SkuImageMapper skuImageMapper;
    @Autowired
    private SkuPlatformAttributeValueMapper skuPlatformAttributeValueMapper;
    @Autowired
    private SkuSaleAttributeValueMapper skuSaleAttributeValueMapper;
    @Autowired
    private SpuSaleAttributeInfoMapper spuSaleAttributeInfoMapper;
    @Autowired
    private PlatformAttributeInfoMapper platformAttributeInfoMapper;
    @Autowired
    private PlatformAttributeValueMapper platformAttributeValueMapper;

    @Autowired
    private SkuInfoConverter skuInfoConverter;
    @Autowired
    private SpuInfoConverter spuInfoConverter;
    @Autowired
    private PlatformAttributeInfoConverter platformAttributeInfoConverter;

    @Autowired
    private SearchServiceClient searchServiceClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSkuInfo(SkuInfoParam skuInfoParam) {

        SkuInfo skuInfo = skuInfoConverter.SkuInfoParam2Po(skuInfoParam);
        skuInfoMapper.insert(skuInfo);
        skuInfoConverter.skuImageParamList2PoList(skuInfoParam.getSkuImageList()).forEach(skuImage -> {
            skuImage.setSkuId(skuInfo.getId());
            skuImageMapper.insert(skuImage);
        });
        skuInfoConverter.skuPlatformAttributeValueParamList2PoList(skuInfoParam.getSkuAttrValueList()).forEach(skuPlatformAttributeValue -> {
            skuPlatformAttributeValue.setSkuId(skuInfo.getId());
            skuPlatformAttributeValueMapper.insert(skuPlatformAttributeValue);
        });
        skuInfoConverter.skuSaleAttributeValueParamList2PoList(skuInfoParam.getSkuSaleAttrValueList()).forEach(skuSaleAttributeValue -> {
            skuSaleAttributeValue.setSkuId(skuInfo.getId());
            skuSaleAttributeValue.setSpuId(skuInfo.getSpuId());
            skuSaleAttributeValueMapper.insert(skuSaleAttributeValue);
        });
    }

    @Override
    public SkuInfoDto getSkuInfo(Long skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectObjectById(skuId);
        return skuInfoConverter.skuInfoPo2Dto(skuInfo);
    }

    @Override
    public SkuInfoPageDto getSkuInfoPage(Page<SkuInfo> pageParam) {
        Page<SkuInfo> skuInfoPage = skuInfoMapper.selectPage(pageParam, new LambdaQueryWrapper<SkuInfo>());
        List<Long> skuInfoIdList = skuInfoPage.getRecords().stream().map(SkuInfo::getId).toList();
        if (skuInfoIdList == null || skuInfoIdList.isEmpty()) {
            return null;
        }
        List<SkuInfo> skuInfoList = skuInfoMapper.selectObjectListByIdList(skuInfoIdList);
        skuInfoPage.setRecords(skuInfoList);
        return skuInfoConverter.skuInfoPoPage2PageDto(skuInfoPage);
    }

    @Override
    public void onSale(Long skuId) {
        updateSale(skuId, true);
        searchServiceClient.upperGoods(skuId);
    }

    @Override
    public void offSale(Long skuId) {
        updateSale(skuId, false);
        searchServiceClient.lowerGoods(skuId);
    }

    private void updateSale(Long skuId, boolean isSale) {
        SkuInfo skuInfoUpdate = new SkuInfo();
        skuInfoUpdate.setId(skuId);
        skuInfoUpdate.setIsSale(isSale ? 1 : 0);
        skuInfoMapper.updateById(skuInfoUpdate);
    }

    @Override
    public BigDecimal getPrice(Long skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        return skuInfo.getPrice();
    }

    @Override
    public List<SpuSaleAttributeInfoDto> getSpuSaleAttributeInfoList(Long spuId, Long skuId) {
        List<SpuSaleAttributeInfo> spuSaleAttributeInfoList = spuSaleAttributeInfoMapper.selectCheckedObject(spuId, skuId);
        return spuInfoConverter.spuSaleAttributeInfoPoList2DtoList(spuSaleAttributeInfoList);
    }

    @Override
    public List<PlatformAttributeInfoDto> getPlatformAttrInfoBySkuId(Long skuId) {
        LambdaQueryWrapper<SkuPlatformAttributeValue> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(SkuPlatformAttributeValue::getSkuId, skuId);
        List<SkuPlatformAttributeValue> skuPlatformAttributeValueList = skuPlatformAttributeValueMapper.selectList(lambdaQueryWrapper);
        List<PlatformAttributeInfo> platformAttributeInfoList = skuPlatformAttributeValueList.stream()
                .map(skuPlatformAttributeValue -> {
                    Long attributeId = skuPlatformAttributeValue.getAttrId();
                    Long valueId = skuPlatformAttributeValue.getValueId();
                    PlatformAttributeInfo platformAttributeInfo = platformAttributeInfoMapper.selectById(attributeId);
                    PlatformAttributeValue platformAttributeValue = platformAttributeValueMapper.selectById(valueId);
                    platformAttributeInfo.setAttrValueList(Collections.singletonList(platformAttributeValue));
                    return platformAttributeInfo;
                })
                .toList();
        return platformAttributeInfoConverter.platformAttributeInfoPoList2DtoList(platformAttributeInfoList);
    }
}
