package pers.wayease.duolaimall.search.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.search.pojo.dto
 * @name GoodsDto
 * @description Goods DTO class.
 * @since 2024-10-10 14:16
 */
@Data
public class GoodsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String defaultImg;
    private String title;
    private Double price;
    private Long tmId;
    private String tmName;
    private String tmLogoUrl;
    private Long firstLevelCategoryId;
    private String firstLevelCategoryName;
    private Long secondLevelCategoryId;
    private String secondLevelCategoryName;
    private Long thirdLevelCategoryId;
    private String thirdLevelCategoryName;
    private Long hotScore = 0L;
    private List<SearchAttributeDto> attrs;
}
