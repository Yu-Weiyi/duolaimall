package pers.wayease.duolaimall.user.service;

import pers.wayease.duolaimall.user.pojo.dto.UserLoginDto;
import pers.wayease.duolaimall.user.pojo.param.UserLoginParam;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.user.service
 * @name UserService
 * @description User service interface.
 * @since 2024-10-12 21:39
 */
public interface UserService {

    UserLoginDto login(UserLoginParam userLoginParam, String ip);

    void logout();
}
