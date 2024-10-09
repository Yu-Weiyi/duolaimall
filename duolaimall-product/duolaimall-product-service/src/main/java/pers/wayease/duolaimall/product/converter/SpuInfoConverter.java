package pers.wayease.duolaimall.product.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pers.wayease.duolaimall.product.pojo.dto.*;
import pers.wayease.duolaimall.product.pojo.model.*;
import pers.wayease.duolaimall.product.pojo.param.SpuImageParam;
import pers.wayease.duolaimall.product.pojo.param.SpuInfoParam;
import pers.wayease.duolaimall.product.pojo.param.SpuPosterParam;
import pers.wayease.duolaimall.product.pojo.param.SpuSaleAttributeInfoParam;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.converter
 * @name SpuInfoConverter
 * @description SPU info converter interface.
 * @since 2024-10-08 21:35
 */
@Mapper(componentModel = "spring")
public interface SpuInfoConverter {

    @Mapping(source = "category3Id", target = "thirdLevelCategoryId")
    @Mapping(source = "spuSaleAttrList", target = "spuSaleAttributeInfoList")
    SpuInfo spuInfoParam2Po(SpuInfoParam spuInfoParam);

    @Mapping(source = "baseSaleAttrId", target = "saleAttrId")
    SpuSaleAttributeInfo spuSaleAttributeParam2Po(SpuSaleAttributeInfoParam spuSaleAttributeInfoParam);

    SpuImage spuImageParam2Po(SpuImageParam spuImageParam);

    List<SpuImage> spuImageParamList2PoList(List<SpuImageParam> spuImageParamList);

    SpuPoster spuPosterParam2Po(SpuPosterParam spuPosterParam);

    List<SpuPoster> spuPosterParamList2PoList(List<SpuPosterParam> spuPosterParamList);

    List<SpuSaleAttributeInfo> spuSaleAttributeParamList2PoList(List<SpuSaleAttributeInfoParam> spuSaleAttributeInfoParamList);

    SpuInfoPageDto spuInfoPoPage2PageDto(Page<SpuInfo> spuInfoPage);

    SpuImageDto spuImagePo2Dto(SpuImage SpuImage);

    List<SpuImageDto> spuImagePoList2DtoList(List<SpuImage> spuImageList);

    SpuPosterDto spuPosterPo2Dto(SpuPoster spuPoster);

    List<SpuPosterDto> spuPosterPoList2DtoList(List<SpuPoster> spuPosterList);

    SpuSaleAttributeInfoDto spuSaleAttributePo2Dto(SpuSaleAttributeInfo spuSaleAttributeInfo);

    List<SpuSaleAttributeInfoDto> spuSaleAttributePoList2DtoList(List<SpuSaleAttributeInfo> spuSaleAttributeInfoList);

    SpuSaleAttributeValueDto spuSaleAttributeValuePo2Dto(SpuSaleAttributeValue spuSaleAttributeValue);

    List<SpuSaleAttributeValueDto> spuSaleAttributeValuePoList2DtoList(List<SpuSaleAttributeValue> spuSaleAttributeValueList);
}
