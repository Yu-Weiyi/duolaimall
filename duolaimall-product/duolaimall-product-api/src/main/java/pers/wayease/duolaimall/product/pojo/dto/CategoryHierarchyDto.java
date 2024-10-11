package pers.wayease.duolaimall.product.pojo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.dto
 * @name CategoryHierarchyDto
 * @description Category hierarchy DTO class.
 * @since 2024-10-09 17:07
 */
@Data
@NoArgsConstructor
public class CategoryHierarchyDto {

    private Long id;
    private Long firstLevelCategoryId;
    private String firstLevelCategoryName;
    private Long secondLevelCategoryId;
    private String secondLevelCategoryName;
    private Long thirdLevelCategoryId;
    private String thirdLevelCategoryName;
}
