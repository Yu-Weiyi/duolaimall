package pers.wayease.duolaimall.product.pojo.dto;

import lombok.Data;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.dto
 * @name SpuImageDto
 * @description SPU image DTO class.
 * @since 2024-10-08 20:52
 */
@Data
public class SpuImageDto {

    private Long id;
    private Long skuId;
    private String imgName;
    private String imgUrl;
    private Long spuImgId;
    private String isDefault;
}
