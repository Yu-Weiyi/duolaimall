package pers.wayease.duolaimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.wayease.duolaimall.product.converter.SkuInfoConverter;
import pers.wayease.duolaimall.product.mapper.SkuImageMapper;
import pers.wayease.duolaimall.product.mapper.SkuInfoMapper;
import pers.wayease.duolaimall.product.mapper.SkuPlatformAttributeValueMapper;
import pers.wayease.duolaimall.product.mapper.SkuSaleAttributeValueMapper;
import pers.wayease.duolaimall.product.pojo.dto.page.SkuInfoPageDto;
import pers.wayease.duolaimall.product.pojo.model.SkuInfo;
import pers.wayease.duolaimall.product.pojo.param.SkuInfoParam;
import pers.wayease.duolaimall.product.service.SkuService;

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
    private SkuInfoConverter skuInfoConverter;

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
    public SkuInfoPageDto getPage(Page<SkuInfo> pageParam) {
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
    }

    @Override
    public void offSale(Long skuId) {
        updateSale(skuId, false);
    }

    private void updateSale(Long skuId, boolean isSale) {
        SkuInfo skuInfoUpdate = new SkuInfo();
        skuInfoUpdate.setId(skuId);
        skuInfoUpdate.setIsSale(isSale ? 1 : 0);
        skuInfoMapper.updateById(skuInfoUpdate);
    }
}
