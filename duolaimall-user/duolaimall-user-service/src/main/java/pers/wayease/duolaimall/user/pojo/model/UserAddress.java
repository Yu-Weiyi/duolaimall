package pers.wayease.duolaimall.user.pojo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import pers.wayease.duolaimall.common.pojo.model.BaseEntity;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.user.pojo.model
 * @name UserAddress
 * @description User address class.
 * @since 2024-10-12 21:56
 */
@TableName("user_address")
public class UserAddress extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("user_address")
    private String userAddress;
    @TableField("user_id")
    private Long userId;
    @TableField("consignee")
    private String consignee;
    @TableField("phone_num")
    private String phoneNum;
    @TableField("is_default")
    private String isDefault;
}
