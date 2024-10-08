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
 * @name Trademark
 * @description Trademark class.
 * @since 2024-10-08 17:10
 */
@TableName("trademark")
@Data
public class Trademark extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("tm_name")
    private String tmName;
    @TableField("logo_url")
    private String logoUrl;
}
