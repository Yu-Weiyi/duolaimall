package pers.wayease.duolaimall.product.pojo.param;

import lombok.Data;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.param
 * @name SpuSaleAttributeInfoParam
 * @description SPU sale attribute info param class.
 * @since 2024-10-08 21:11
 */
@Data
public class SpuSaleAttributeInfoParam {

    private Long id;
    private Long spuId;
    private Long baseSaleAttrId;
    private String saleAttrName;
    List<SpuSaleAttributeValueParam> spuSaleAttrValueList;
}
