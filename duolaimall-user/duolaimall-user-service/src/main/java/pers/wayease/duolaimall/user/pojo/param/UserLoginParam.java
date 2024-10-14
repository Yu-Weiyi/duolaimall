package pers.wayease.duolaimall.user.pojo.param;

import lombok.Data;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.user.pojo.param
 * @name UserLoginParam
 * @description User login param class.
 * @since 2024-10-12 21:31
 */
@Data
public class UserLoginParam {

    // email or nick name or phone
    private String loginName;
    private String passwd;
}
