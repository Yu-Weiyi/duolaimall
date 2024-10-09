package pers.wayease.duolaimall.product.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.dto
 * @name SpuInfoDto
 * @description SPU info DTO class.
 * @since 2024-10-08 20:30
 */
@Data
public class SpuInfoDto {

    private Long id;
    private String spuName;
    private String description;
    private Long thirdLevelCategoryId;
    private Long tmId;
    private List<SpuSaleAttributeInfoDto> spuSaleAttributeInfoList;
    private List<SpuImageDto> SpuImageList;
    private List<SpuPosterDto> SpuPosterList;
}
