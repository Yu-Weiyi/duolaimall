package pers.wayease.duolaimall.product.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.dto
 * @name SkuInfoDto
 * @description SKU info DTO class.
 * @since 2024-10-09 14:22
 */
@Data
public class SkuInfoDto {

    private Long id;
    private Long spuId;
    private BigDecimal price;
    private String skuName;
    private String skuDesc;
    private String weight;
    private Long tmId;
    private Long thirdLevelCategoryId;
    private String skuDefaultImg;
    private Integer isSale;
    List<SkuImageDto> skuImageList;
    List<SkuPlatformAttributeValueDto> skuPlatformAttributeValueList;
    List<SkuSaleAttributeValueDto> skuSaleAttributeValueList;
}
