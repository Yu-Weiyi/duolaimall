package pers.wayease.duolaimall.product.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.dto
 * @name FirstLevelCategoryNodeDto
 * @description First level category node DTO class.
 * @since 2024-10-10 02:22
 */
@Data
public class FirstLevelCategoryNodeDto {

    Long categoryId;
    String categoryName;
    List<SecondLevelCategoryNodeDto> categoryChild;
}
