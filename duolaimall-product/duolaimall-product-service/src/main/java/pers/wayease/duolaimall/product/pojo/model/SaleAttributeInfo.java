package pers.wayease.duolaimall.product.pojo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import pers.wayease.duolaimall.common.pojo.model.BaseEntity;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.pojo.model
 * @name SaleAttributeInfo
 * @description Sale attribute info class.
 * @since 2024-10-08 20:59
 */
@TableName("sale_attr_info")
@Data
public class SaleAttributeInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("name")
    private String name;
}
