package pers.wayease.duolaimall.product.pojo.dto;

import lombok.Data;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.dto
 * @name ThirdLevelCategoryDto
 * @description Third level category DTO class.
 * @since 2024-10-06 20:21
 */
@Data
public class ThirdLevelCategoryDto {

    private Long id;
    private String name;
    private Long secondLevelCategoryId;
}
