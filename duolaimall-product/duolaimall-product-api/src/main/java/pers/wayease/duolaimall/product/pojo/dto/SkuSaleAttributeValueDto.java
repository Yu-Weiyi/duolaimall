package pers.wayease.duolaimall.product.pojo.dto;

import lombok.Data;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.dto
 * @name SkuSaleAttributeValueDto
 * @description SKU sale attribute value DTO class.
 * @since 2024-10-09 14:27
 */
@Data
public class SkuSaleAttributeValueDto {

    private Long id;
    private Long skuId;
    private Long spuId;
    private Long saleAttrValueId;
}
