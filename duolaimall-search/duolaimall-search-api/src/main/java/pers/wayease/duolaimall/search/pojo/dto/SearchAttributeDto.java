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
 * @name SearchAttributeDto
 * @description Search attribute DTO class.
 * @since 2024-10-10 14:15
 */
@Data
public class SearchAttributeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long attrId;
    private List<String> attrValueList = new ArrayList<>();
    private String attrName;
}
