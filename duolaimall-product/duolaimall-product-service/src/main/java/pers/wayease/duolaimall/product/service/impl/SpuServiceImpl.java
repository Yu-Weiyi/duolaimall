package pers.wayease.duolaimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.wayease.duolaimall.product.converter.SpuInfoConverter;
import pers.wayease.duolaimall.product.mapper.*;
import pers.wayease.duolaimall.product.pojo.dto.SpuImageDto;
import pers.wayease.duolaimall.product.pojo.dto.SpuInfoPageDto;
import pers.wayease.duolaimall.product.pojo.dto.SpuSaleAttributeInfoDto;
import pers.wayease.duolaimall.product.pojo.model.SpuImage;
import pers.wayease.duolaimall.product.pojo.model.SpuInfo;
import pers.wayease.duolaimall.product.pojo.model.SpuSaleAttributeInfo;
import pers.wayease.duolaimall.product.pojo.param.SpuInfoParam;
import pers.wayease.duolaimall.product.service.SpuService;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 0.1
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.service.impl
 * @name SpuServiceImpl
 * @description SPU service implement class.
 * @since 2024-10-08 21:18
 */
@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuInfoMapper spuInfoMapper;
    @Autowired
    private SpuSaleAttributeInfoMapper spuSaleAttributeInfoMapper;
    @Autowired
    private SpuSaleAttributeValueMapper spuSaleAttributeValueMapper;
    @Autowired
    private SpuImageMapper spuImageMapper;
    @Autowired
    private SpuPosterMapper spuPosterMapper;

    @Autowired
    private SpuInfoConverter spuInfoConverter;

    @Override
    public SpuInfoPageDto getSpuInfoPage(Page<SpuInfo> pageParam, Long thirdLevelCategoryId) {
        LambdaQueryWrapper<SpuInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(SpuInfo::getThirdLevelCategoryId, thirdLevelCategoryId);
        Page<SpuInfo> spuInfoPage = spuInfoMapper.selectPage(pageParam, lambdaQueryWrapper);
        List<Long> spuInfoIdList = spuInfoPage.getRecords().stream()
                .map(SpuInfo::getId)
                .toList();
        if (spuInfoIdList == null || spuInfoIdList.isEmpty()) {
            return null;
        }
        List<SpuInfo> spuInfoList = spuInfoMapper.selectListByspuInfoIdList(spuInfoIdList);
        spuInfoPage.setRecords(spuInfoList);
        return spuInfoConverter.spuInfoPoPage2PageDto(spuInfoPage);
    }

    // TODO async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSpuInfo(SpuInfoParam spuInfoParam) {
        SpuInfo spuInfo = spuInfoConverter.spuInfoParam2Po(spuInfoParam);
        spuInfoMapper.insert(spuInfo);
        spuInfoConverter.spuSaleAttributeParamList2PoList(spuInfoParam.getSpuSaleAttrList()).forEach(spuSaleAttributeInfo -> {
            spuSaleAttributeInfo.setSpuId(spuInfo.getId());
            spuSaleAttributeInfoMapper.insert(spuSaleAttributeInfo);
            spuSaleAttributeInfo.getSpuSaleAttrValueList().forEach(spuSaleAttributeValue -> {
                spuSaleAttributeValue.setSpuId(spuInfo.getId());
                spuSaleAttributeValue.setSpuSaleAttrId(spuSaleAttributeInfo.getId());
                spuSaleAttributeValueMapper.insert(spuSaleAttributeValue);
            });
        });
        spuInfoConverter.spuImageParamList2PoList(spuInfoParam.getSpuImageList()).forEach(spuImage -> {
            spuImage.setSpuId(spuInfo.getId());
            spuImageMapper.insert(spuImage);
        });
        spuInfoConverter.spuPosterParamList2PoList(spuInfoParam.getSpuPosterList()).forEach(spuPoster -> {
            spuPoster.setSpuId(spuInfo.getId());
            spuPosterMapper.insert(spuPoster);
        });
    }

    @Override
    public List<SpuImageDto> getSpuImageList(Long spuId) {
        LambdaQueryWrapper<SpuImage> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(SpuImage::getSpuId, spuId);
        List<SpuImage> spuImageList = spuImageMapper.selectList(lambdaQueryWrapper);
        return spuInfoConverter.spuImagePoList2DtoList(spuImageList);
    }

    @Override
    public List<SpuSaleAttributeInfoDto> getSpuSaleAttributeList(Long spuId) {
        List<SpuSaleAttributeInfo> spuSaleAttributeInfoList = spuSaleAttributeInfoMapper.selectObjectListBySpuId(spuId);
        return spuInfoConverter.spuSaleAttributePoList2DtoList(spuSaleAttributeInfoList);
    }
}
