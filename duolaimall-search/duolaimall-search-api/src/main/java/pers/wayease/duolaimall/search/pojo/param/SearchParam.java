package pers.wayease.duolaimall.search.pojo.param;

import lombok.Data;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.search.pojo.param
 * @name SearchParam
 * @description Search param class.
 * @since 2024-10-10 14:19
 */
@Data
public class SearchParam {

    private Long firstLevelCategoryId;
    private Long secondLevelCategoryId;
    private Long thirdLevelCategoryId;
    private String trademark;
    private String keyword;
    // 1:hotScore 2:price
    private String order = "";
    private String[] props;
    private Integer pageNo = 1;
    private Integer pageSize = 8;
}
