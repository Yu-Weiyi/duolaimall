package pers.wayease.duolaimall.search.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.search.pojo.dto
 * @name SearchDto
 * @description Search DTO class.
 * @since 2024-10-10 14:09
 */
@Data
public class SearchPageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<SearchTrademarkDto> trademarkList;
    private List<SearchAttributeDto> attrsList;
    private List<GoodsDto> goodsList = new ArrayList<>();
    private Long total;
    private Integer pageSize;
    private Integer pageNo;
    private Long totalPages;
}
