package pers.wayease.duolaimall.product.pojo.param;

import lombok.Data;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.param
 * @name SkuSaleAttributeValueParam
 * @description SKU sale attribute value param class.
 * @since 2024-10-09 14:19
 */
@Data
public class SkuSaleAttributeValueParam {

    private Long id;
    private Long skuId;
    private Long spuId;
    private Long saleAttrValueId;
}
