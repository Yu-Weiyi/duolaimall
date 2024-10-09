package pers.wayease.duolaimall.product.pojo.param;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.param
 * @name SkuInfoParam
 * @description SKU info param class.
 * @since 2024-10-09 14:15
 */
@Data
public class SkuInfoParam {

    private Long id;
    private Long spuId;
    private BigDecimal price;
    private String skuName;
    private String skuDesc;
    private String weight;
    private Long tmId;
    private Long category3Id;
    private String skuDefaultImg;
    private Integer isSale;
    List<SkuImageParam> skuImageList;
    List<SkuPlatformAttributeValueParam> skuAttrValueList;
    List<SkuSaleAttributeValueParam> skuSaleAttrValueList;
}
