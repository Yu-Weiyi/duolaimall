package pers.wayease.duolaimall.product.pojo.param;

import lombok.Data;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.param
 * @name SpuSaleAttributeValueParam
 * @description SPU sale attribute value param class.
 * @since 2024-10-08 21:14
 */
@Data
public class SpuSaleAttributeValueParam {

    private Long id;
    private Long baseSaleAttrId;
    private Long spuId;
    private String saleAttrValueName;
}
