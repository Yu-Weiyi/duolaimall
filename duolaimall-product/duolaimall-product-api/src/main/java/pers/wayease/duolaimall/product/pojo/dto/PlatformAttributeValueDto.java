package pers.wayease.duolaimall.product.pojo.dto;

import lombok.Data;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.dto
 * @name PlatformAttributeValueDto
 * @description Platform attribute value DTO class.
 * @since 2024-10-08 09:46
 */
@Data
public class PlatformAttributeValueDto {

    private Long id;
    private String valueName;
    private Long attrId;
}
