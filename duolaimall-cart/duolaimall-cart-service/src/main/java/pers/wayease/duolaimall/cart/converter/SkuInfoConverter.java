package pers.wayease.duolaimall.cart.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pers.wayease.duolaimall.cart.pojo.dto.CartInfoDto;
import pers.wayease.duolaimall.product.pojo.dto.SkuInfoDto;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.cart.converter
 * @name SkuInfoConverter
 * @description SKU info converter interface.
 * @since 2024-10-14 20:26
 */
@Mapper(componentModel = "spring")
public interface SkuInfoConverter {

    @Mapping(source = "skuId",target = "skuId")
    @Mapping(source = "skuInfo.price", target = "cartPrice")
    @Mapping(source = "skuNum", target = "skuNum")
    @Mapping(source = "skuInfo.skuDefaultImg", target = "imgUrl")
    @Mapping(source = "skuInfo.skuName",target = "skuName")
    @Mapping(target = "isChecked",constant = "1")
    @Mapping(source = "skuInfo.price", target = "skuPrice")
    @Mapping(target = "createTime", expression = "java( new java.util.Date())")
    @Mapping(target = "updateTime", expression = "java( new java.util.Date())")
    @Mapping(target = "userId",source = "userId")
    CartInfoDto skuInfoDto2CartInfoDto(SkuInfoDto skuInfo, Integer skuNum, Long skuId, String userId);
}
