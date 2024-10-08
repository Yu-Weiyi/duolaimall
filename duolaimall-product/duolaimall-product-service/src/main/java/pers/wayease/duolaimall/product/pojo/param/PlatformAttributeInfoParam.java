package pers.wayease.duolaimall.product.pojo.param;

import lombok.Data;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.param
 * @name PlatformAttributeInfoParam
 * @description Platform attribute info param class.
 * @since 2024-10-08 09:31
 */
@Data
public class PlatformAttributeInfoParam {

    private Long id;
    private String attrName;
    private Long categoryId;
    private Integer categoryLevel;
    private List<PlatformAttributeValueParam> attrValueList;
}
