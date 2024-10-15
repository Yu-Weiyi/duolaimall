package pers.wayease.duolaimall.ware.pojo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import pers.wayease.duolaimall.common.pojo.model.BaseEntity;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.ware.pojo.model
 * @name WareInfo
 * @description Ware info class.
 * @since 2024-10-15 20:28
 */
@TableName("ware_info")
@Data
public class WareInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField
    private String name;
    @TableField
    private String address;
    @TableField
    private String areacode;
}
