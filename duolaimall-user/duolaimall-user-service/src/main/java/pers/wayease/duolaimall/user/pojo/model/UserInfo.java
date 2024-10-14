package pers.wayease.duolaimall.user.pojo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import pers.wayease.duolaimall.common.pojo.model.BaseEntity;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.user.pojo.model
 * @name UserInfo
 * @description User info class.
 * @since 2024-10-12 21:53
 */
@TableName("user_info")
@Data
public class UserInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("login_name")
    private String loginName;
    @TableField("nick_name")
    private String nickName;
    @TableField("passwd")
    private String passwd;
    @TableField("name")
    private String name;
    @TableField("phone_num")
    private String phoneNum;
    @TableField("email")
    private String email;
    @TableField("head_img")
    private String headImg;
    @TableField("user_level")
    private String userLevel;
}
