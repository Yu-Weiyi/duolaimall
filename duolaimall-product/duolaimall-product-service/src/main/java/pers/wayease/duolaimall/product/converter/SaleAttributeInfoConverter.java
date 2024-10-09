package pers.wayease.duolaimall.product.converter;

import org.mapstruct.Mapper;
import pers.wayease.duolaimall.product.pojo.dto.SaleAttributeInfoDto;
import pers.wayease.duolaimall.product.pojo.model.SaleAttributeInfo;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.converter
 * @name SaleAttributeInfoConverter
 * @description Sale attribute info converter interface.
 * @since 2024-10-08 21:03
 */
@Mapper(componentModel = "spring")
public interface SaleAttributeInfoConverter {

    SaleAttributeInfoDto saleAttributeInfoPo2Dto(SaleAttributeInfo saleAttributeInfo);

    List<SaleAttributeInfoDto> saleAttributeInfoPoList2DtoList(List<SaleAttributeInfo> saleAttributeInfoList);
}
