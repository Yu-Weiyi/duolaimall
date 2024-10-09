package pers.wayease.duolaimall.product.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pers.wayease.duolaimall.product.pojo.dto.SkuInfoPageDto;
import pers.wayease.duolaimall.product.pojo.model.SkuImage;
import pers.wayease.duolaimall.product.pojo.model.SkuInfo;
import pers.wayease.duolaimall.product.pojo.model.SkuPlatformAttributeValue;
import pers.wayease.duolaimall.product.pojo.model.SkuSaleAttributeValue;
import pers.wayease.duolaimall.product.pojo.param.SkuImageParam;
import pers.wayease.duolaimall.product.pojo.param.SkuInfoParam;
import pers.wayease.duolaimall.product.pojo.param.SkuPlatformAttributeValueParam;
import pers.wayease.duolaimall.product.pojo.param.SkuSaleAttributeValueParam;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.converter
 * @name SkuInfoConverter
 * @description SKU info converter interface.
 * @since 2024-10-09 14:48
 */
@Mapper(componentModel = "spring")
public interface SkuInfoConverter {

    @Mapping(source = "category3Id", target = "thirdLevelCategoryId")
    @Mapping(source = "skuAttrValueList", target = "skuPlatformAttributeValueList")
    @Mapping(source = "skuSaleAttrValueList", target = "skuSaleAttributeValueList")
    SkuInfo SkuInfoParam2Po(SkuInfoParam skuInfoParam);

    SkuImage skuImageParam2Po(SkuImageParam skuImageParams);

    List<SkuImage> skuImageParamList2PoList(List<SkuImageParam> skuImageParamList);

    SkuPlatformAttributeValue skuPlatformAttributeValueParam2Po(SkuPlatformAttributeValueParam skuPlatformAttributeValueParam);

    List<SkuPlatformAttributeValue> skuPlatformAttributeValueParamList2PoList(List<SkuPlatformAttributeValueParam> skuPlatformAttributeValueParamList);

    @Mapping(source = "saleAttrValueId", target = "spuSaleAttrValueId")
    SkuSaleAttributeValue skuSaleAttributeValueParam2Po(SkuSaleAttributeValueParam skuSaleAttributeValueParam);

    List<SkuSaleAttributeValue> skuSaleAttributeValueParamList2PoList(List<SkuSaleAttributeValueParam> skuSaleAttributeValueParamList);

    SkuInfoPageDto skuInfoPoPage2PageDto(Page<SkuInfo> skuInfoPage);
}
