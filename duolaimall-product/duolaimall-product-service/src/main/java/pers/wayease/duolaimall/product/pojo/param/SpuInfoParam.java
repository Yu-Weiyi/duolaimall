package pers.wayease.duolaimall.product.pojo.param;

import lombok.Data;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.param
 * @name SpuInfoParam
 * @description SPU info param class.
 * @since 2024-10-08 21:10
 */
@Data
public class SpuInfoParam {

    private Long id;
    private String spuName;
    private String description;
    private Long category3Id;
    private Long tmId;
    private List<SpuSaleAttributeInfoParam> spuSaleAttrList;
    private List<SpuImageParam> SpuImageList;
    private List<SpuPosterParam> SpuPosterList;
}
