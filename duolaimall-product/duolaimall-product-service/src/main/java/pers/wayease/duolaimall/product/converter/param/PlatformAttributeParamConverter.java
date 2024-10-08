package pers.wayease.duolaimall.product.converter.param;

import org.mapstruct.Mapper;
import pers.wayease.duolaimall.product.pojo.model.PlatformAttributeInfo;
import pers.wayease.duolaimall.product.pojo.model.PlatformAttributeValue;
import pers.wayease.duolaimall.product.pojo.param.PlatformAttributeInfoParam;
import pers.wayease.duolaimall.product.pojo.param.PlatformAttributeValueParam;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.converter.dto
 * @name PlatformAttributeParamConverter
 * @description Platform attribute param converter interface.
 * @since 2024-10-08 13:59
 */
@Mapper(componentModel = "spring")
public interface PlatformAttributeParamConverter {

    PlatformAttributeInfo platformAttributeInfoParam2Po(PlatformAttributeInfoParam platformAttributeInfoParam);

    PlatformAttributeValue platformAttributeValueParam2Po(PlatformAttributeValueParam platformAttributeValueParam);
}
