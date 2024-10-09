package pers.wayease.duolaimall.product.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.dto
 * @name ProductDetailDto
 * @description Product detail DTO class.
 * @since 2024-10-09 17:05
 */
@Data
public class ProductDetailDto {

    SkuInfoDto skuInfo;
    List<SpuSaleAttributeInfoDto> spuSaleAttrList;
    String valuesSkuJson;
    BigDecimal price;
    CategoryHierarchyDto categoryHierarchy;
    List<SpuPosterDto> spuPosterList;
    List<SkuSpecificationDto> skuAttrList;
}
