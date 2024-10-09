package pers.wayease.duolaimall.product.pojo.param;

import lombok.Data;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.param
 * @name SkuImageParam
 * @description SKU image param class.
 * @since 2024-10-09 14:17
 */
@Data
public class SkuImageParam {

    private Long id;
    private Long skuId;
    private String imgName;
    private String imgUrl;
    private Long spuImgId;
    private String isDefault;
}
