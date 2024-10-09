package pers.wayease.duolaimall.product.pojo.param;

import lombok.Data;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.param
 * @name SpuPosterParam
 * @description SPU poster param class.
 * @since 2024-10-08 21:16
 */
@Data
public class SpuPosterParam {

    private Long id;
    private Long spuId;
    private String imgName;
    private String imgUrl;
}
