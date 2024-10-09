package pers.wayease.duolaimall.product.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.dto
 * @name SecondLevelCategoryNodeDto
 * @description Second level category node DTO class.
 * @since 2024-10-10 02:23
 */
@Data
public class SecondLevelCategoryNodeDto {

    Long categoryId;
    String categoryName;
    List<ThirdLevelCategoryNodeDto> categoryChild;
}
