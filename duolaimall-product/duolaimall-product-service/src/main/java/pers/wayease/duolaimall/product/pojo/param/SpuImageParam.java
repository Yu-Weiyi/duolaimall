package pers.wayease.duolaimall.product.pojo.param;

import lombok.Data;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.param
 * @name SpuImageParam
 * @description SPU image param class.
 * @since 2024-10-08 21:15
 */
@Data
public class SpuImageParam {

    private Long id;
    private Long skuId;
    private String imgName;
    private String imgUrl;
    private Long spuImgId;
    private String isDefault;
}
