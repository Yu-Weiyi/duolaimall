package pers.wayease.duolaimall.user.controller.website;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pers.wayease.duolaimall.common.constant.HeaderConstant;
import pers.wayease.duolaimall.common.constant.ResultCodeEnum;
import pers.wayease.duolaimall.common.result.Result;
import pers.wayease.duolaimall.common.util.IpUtil;
import pers.wayease.duolaimall.user.pojo.dto.UserLoginDto;
import pers.wayease.duolaimall.user.pojo.param.UserLoginParam;
import pers.wayease.duolaimall.user.service.JwtAuthService;
import pers.wayease.duolaimall.user.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.user.controller.website
 * @name UserController
 * @description User controller class.
 * @since 2024-10-12 21:24
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtAuthService jwtAuthService;

    @PostMapping("/login")
    public Result<UserLoginDto> login(@RequestBody UserLoginParam userLoginParam, HttpServletRequest httpServletRequest) {
        String ip = IpUtil.getIpAddress(httpServletRequest);
        UserLoginDto userLoginDto = userService.login(userLoginParam, ip);
        return Result.ok(userLoginDto);
    }

    // after auth
    @GetMapping("/logout")
    public Result<Void> logout() {
        userService.logout();
        return Result.ok();
    }

    @RequestMapping("/auth")
    public ResponseEntity<Result<Void>> authenticate(@RequestHeader(HeaderConstant.TOKEN_HEADER) String jwtToken) {
        log.info("Request need auth, token: {}.", jwtToken);
        if (jwtToken == null || jwtToken.isEmpty()) {
            log.info("Token is null or empty, returning unauthorized.");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Result.fail(null, ResultCodeEnum.LOGIN_AUTH));
        }

        Long userId = jwtAuthService.validateToken(jwtToken);
        if (userId == null || userId <= 0) {
            log.info("Invalid token, returning unauthorized.");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Result.fail(null, ResultCodeEnum.LOGIN_AUTH));
        }

        // pass
        log.info("User {} authenticated successfully.", userId);
        return ResponseEntity
                .ok()
                .header(HeaderConstant.USER_ID_HEADER, String.valueOf(userId))
                .body(Result.ok());
    }
}
