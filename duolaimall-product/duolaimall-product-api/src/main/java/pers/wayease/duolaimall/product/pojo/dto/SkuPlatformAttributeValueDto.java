package pers.wayease.duolaimall.product.pojo.dto;

import lombok.Data;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.dto
 * @name SkuPlatformAttributeValueDto
 * @description SKU platform attribute value DTO class.
 * @since 2024-10-09 14:26
 */
@Data
public class SkuPlatformAttributeValueDto {

    private Long id;
    private Long attrId;
    private Long valueId;
    private Long skuId;
}
