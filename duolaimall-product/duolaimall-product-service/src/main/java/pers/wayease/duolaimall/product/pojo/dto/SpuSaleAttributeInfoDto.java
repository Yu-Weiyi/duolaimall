package pers.wayease.duolaimall.product.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.dto
 * @name SpuSaleAttributeInfoDto
 * @description SPU sale attribute info DTO class.
 * @since 2024-10-08 20:32
 */
@Data
public class SpuSaleAttributeInfoDto {

    private Long id;
    private Long spuId;
    private Long saleAttrId;
    private String saleAttrName;
    List<SpuSaleAttributeValueDto> spuSaleAttrValueList;
}
