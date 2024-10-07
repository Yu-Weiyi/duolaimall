package pers.wayease.duolaimall.product.pojo.dto;

import lombok.Data;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.dto
 * @name SecondLevelCategoryDto
 * @description Second level category DTO class.
 * @since 2024-10-06 20:20
 */
@Data
public class SecondLevelCategoryDto {

    private Long id;
    private String name;
    private Long firstLevelCategoryId;
}
