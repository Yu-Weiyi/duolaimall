package pers.wayease.duolaimall.product.service;

import pers.wayease.duolaimall.product.pojo.dto.PlatformAttributeInfoDto;
import pers.wayease.duolaimall.product.pojo.dto.PlatformAttributeValueDto;
import pers.wayease.duolaimall.product.pojo.param.PlatformAttributeInfoParam;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.service
 * @name PlatformAttributeService
 * @description Platform attribute service interface.
 * @since 2024-10-08 09:27
 */
public interface PlatformAttributeService {

    void savePlatformAttributeInfo(PlatformAttributeInfoParam platformAttributeInfoParam);

    List<PlatformAttributeInfoDto> getPlatformAttributeInfoList(Long firstLevelCategoryId, Long secondLevelCategoryId, Long thirdLevelCategoryId);

    List<PlatformAttributeValueDto> getPlatformAttributeValueList(Long platformAttributeId);
}
