package pers.wayease.duolaimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.wayease.duolaimall.product.converter.PlatformAttributeConverter;
import pers.wayease.duolaimall.product.mapper.PlatformAttributeInfoMapper;
import pers.wayease.duolaimall.product.mapper.PlatformAttributeValueMapper;
import pers.wayease.duolaimall.product.pojo.dto.PlatformAttributeInfoDto;
import pers.wayease.duolaimall.product.pojo.dto.PlatformAttributeValueDto;
import pers.wayease.duolaimall.product.pojo.model.PlatformAttributeInfo;
import pers.wayease.duolaimall.product.pojo.model.PlatformAttributeValue;
import pers.wayease.duolaimall.product.pojo.param.PlatformAttributeInfoParam;
import pers.wayease.duolaimall.product.service.PlatformAttributeService;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.service.impl
 * @name PlatformAttributeServiceImpl
 * @description Platform attribute service implement class.
 * @since 2024-10-08 09:53
 */
@Service
public class PlatformAttributeServiceImpl implements PlatformAttributeService {

    @Autowired
    private PlatformAttributeInfoMapper platformAttributeInfoMapper;
    @Autowired
    private PlatformAttributeValueMapper platformAttributeValueMapper;

    @Autowired
    private PlatformAttributeConverter platformAttributeConverter;

    @Override
    public void savePlatformAttributeInfo(PlatformAttributeInfoParam platformAttributeInfoParam) {
        Long platformAttributeId = platformAttributeInfoParam.getId();
        PlatformAttributeInfo platformAttributeInfo = platformAttributeConverter.platformAttributeInfoParam2Po(platformAttributeInfoParam);
        if (platformAttributeId != null) {
            // update info
            platformAttributeInfoMapper.updateById(platformAttributeInfo);
            // delete old value list
            LambdaQueryWrapper<PlatformAttributeValue> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper
                    .eq(PlatformAttributeValue::getAttrId, platformAttributeId);
            platformAttributeValueMapper.delete(lambdaQueryWrapper);
        } else {
            platformAttributeInfoMapper.insert(platformAttributeInfo);
            platformAttributeId = platformAttributeInfo.getId();
        }

        // insert new value list
        Long finalPlatformAttributeId = platformAttributeId;
        platformAttributeInfoParam.getAttrValueList().forEach(platformAttributeValueParam -> {
            PlatformAttributeValue platformAttributeValue = platformAttributeConverter.platformAttributeValueParam2Po(platformAttributeValueParam);
            platformAttributeValue.setId(null);
            platformAttributeValue.setAttrId(finalPlatformAttributeId);
            platformAttributeValueMapper.insert(platformAttributeValue);
        });
    }

    @Override
    public List<PlatformAttributeInfoDto> getPlatformAttributeInfoList(Long firstLevelCategoryId, Long secondLevelCategoryId, Long thirdLevelCategoryId) {
        List<PlatformAttributeInfo> platformAttributeInfoList = platformAttributeInfoMapper.selectObjectList(firstLevelCategoryId, secondLevelCategoryId, thirdLevelCategoryId);
        return platformAttributeConverter.platformAttributeInfoPoList2DtoList(platformAttributeInfoList);
    }

    @Override
    public List<PlatformAttributeValueDto> getPlatformAttributeValueList(Long platformAttributeId) {
        LambdaQueryWrapper<PlatformAttributeValue> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(PlatformAttributeValue::getAttrId, platformAttributeId);
        List<PlatformAttributeValue> platformAttributeValueList = platformAttributeValueMapper.selectList(lambdaQueryWrapper);
        return platformAttributeConverter.platformAttributeValuePoList2DtoList(platformAttributeValueList);
    }
}
