package pers.wayease.duolaimall.product.pojo.dto;

import lombok.Data;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.dto
 * @name SpuPosterDto
 * @description SPU poster DTO class.
 * @since 2024-10-08 20:53
 */
@Data
public class SpuPosterDto {

    private Long id;
    private Long spuId;
    private String imgName;
    private String imgUrl;
}