package pers.wayease.duolaimall.user.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.user.pojo.dto.UserAddressDto;
import pers.wayease.duolaimall.user.service.UserService;

import java.util.List;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.user.controller.api
 * @name UserController
 * @description User controller class.
 * @since 2024-10-14 09:39
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUserAddressListByUserId/{userId}")
    public Result<List<UserAddressDto>> getUserAddressListByUserId(@PathVariable Long userId) {
        List<UserAddressDto> userAddressDtoList = userService.getUserAddressListByUserId(userId);
        return Result.ok(userAddressDtoList);
    }
}
