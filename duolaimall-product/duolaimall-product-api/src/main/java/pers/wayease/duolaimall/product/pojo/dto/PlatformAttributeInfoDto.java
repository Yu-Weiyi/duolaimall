package pers.wayease.duolaimall.product.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.dto
 * @name PlatformAttributeInfoDto
 * @description Platform attribute info DTO class.
 * @since 2024-10-08 09:42
 */
@Data
public class PlatformAttributeInfoDto {

    private Long id;
    private String attrName;
    private Long categoryId;
    private Integer categoryLevel;
    private List<PlatformAttributeValueDto> attrValueList;
}
