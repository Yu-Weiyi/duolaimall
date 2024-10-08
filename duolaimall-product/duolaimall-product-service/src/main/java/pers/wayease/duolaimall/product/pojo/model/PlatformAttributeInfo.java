package pers.wayease.duolaimall.product.pojo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.wayease.duolaimall.common.model.BaseEntity;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.model
 * @name PlatformAttributeInfo
 * @description Platform attribute info PO class.
 * @since 2024-10-08 10:52
 */
@TableName("platform_attr_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformAttributeInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("attr_name")
    private String attrName;
    @TableField("category_id")
    private Long categoryId;
    @TableField("category_level")
    private Integer categoryLevel;

    @TableField(exist = false)
    private List<PlatformAttributeValue> attrValueList;
}
