package pers.wayease.duolaimall.product.pojo.param;

import lombok.Data;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.param
 * @name SkuPlatformAttributeValueParam
 * @description SKU platform attribute value param class.
 * @since 2024-10-09 14:18
 */
@Data
public class SkuPlatformAttributeValueParam {

    private Long id;
    private Long attrId;
    private Long valueId;
    private Long skuId;
}
