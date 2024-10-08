package pers.wayease.duolaimall.product.pojo.param;

import lombok.Data;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.param
 * @name PlatformAttributeValueParam
 * @description Platform attribute value param class.
 * @since 2024-10-08 09:38
 */
@Data
public class PlatformAttributeValueParam {

    private Long id;
    private String valueName;
    private Long attrId;
}
