package pers.wayease.duolaimall.product.converter;

import org.mapstruct.Mapper;
import pers.wayease.duolaimall.product.pojo.dto.PlatformAttributeInfoDto;
import pers.wayease.duolaimall.product.pojo.dto.PlatformAttributeValueDto;
import pers.wayease.duolaimall.product.pojo.model.PlatformAttributeInfo;
import pers.wayease.duolaimall.product.pojo.model.PlatformAttributeValue;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.converter
 * @name PlatformAttributeInfoConverter
 * @description Platform attribute info converter interface.
 * @since 2024-10-09 20:14
 */
@Mapper(componentModel = "spring")
public interface PlatformAttributeInfoConverter {

    PlatformAttributeInfoDto platformAttributeInfoPo2Dto(PlatformAttributeInfo platformAttributeInfo);

    List<PlatformAttributeInfoDto> platformAttributeInfoPoList2DtoList(List<PlatformAttributeInfo> platformAttributeInfoList);

    PlatformAttributeValueDto platformAttributeValuePo2Dto(PlatformAttributeValue platformAttributeValue);

    List<PlatformAttributeValueDto> platformAttributeValuePoList2DtoList(List<PlatformAttributeValue> platformAttributeValueList);
}
