package pers.wayease.duolaimall.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pers.wayease.duolaimall.common.constant.RedisConstant;
import pers.wayease.duolaimall.common.constant.ResultCodeEnum;
import pers.wayease.duolaimall.common.context.UserContext;
import pers.wayease.duolaimall.common.exception.BaseException;
import pers.wayease.duolaimall.user.converter.UserConverter;
import pers.wayease.duolaimall.user.mapper.UserAddressMapper;
import pers.wayease.duolaimall.user.mapper.UserInfoMapper;
import pers.wayease.duolaimall.user.pojo.dto.UserAddressDto;
import pers.wayease.duolaimall.user.pojo.dto.UserLoginDto;
import pers.wayease.duolaimall.user.pojo.model.UserAddress;
import pers.wayease.duolaimall.user.pojo.model.UserInfo;
import pers.wayease.duolaimall.user.pojo.param.UserLoginParam;
import pers.wayease.duolaimall.user.service.JwtAuthService;
import pers.wayease.duolaimall.user.service.UserService;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.user.service.impl
 * @name UserServiceImpl
 * @description User service implement class.
 * @since 2024-10-12 21:46
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtAuthService jwtAuthService;

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public UserLoginDto login(UserLoginParam userLoginParam, String ip) {
        // verify login name
        String loginName = userLoginParam.getLoginName();
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper
                .eq(UserInfo::getLoginName, loginName)
                .or()
                .eq(UserInfo::getEmail, loginName)
                .or()
                .eq(UserInfo::getPhoneNum, loginName);
        List<UserInfo> userInfoList = userInfoMapper.selectList(lambdaQueryWrapper);
        switch (userInfoList.size()) {
            // no such account
            case 0:
                log.info("Reject login request, no such login name {}.", loginName);
                throw new BaseException(ResultCodeEnum.LOGIN_WITH_INEXIST_ACCOUNT);
            // passed
            case 1:
                break;
            // controversial account
            default:
                log.warn("Reject login request, login name or phone or email {} match more than one user.", loginName);
                throw new BaseException(ResultCodeEnum.LOGIN_WITH_CONTROVERSIAL_ACCOUNT);
        }
        UserInfo userInfo = userInfoList.get(0);

        // verify password
        String passwd = userLoginParam.getPasswd();
        String storedBcryptPassword = userInfo.getPasswd();
        if (!bCryptPasswordEncoder.matches(passwd, storedBcryptPassword)) {
            // wrong password
            log.info("Reject login request, wrong password of login name {}.", loginName);
            throw new BaseException(ResultCodeEnum.LOGIN_WITH_UNMATCHED_PASSWORD);
        }

        // TODO verify ip

        // verified
        log.info("Pass login request, login name {}.", loginName);

        // generate JWT token
        Long id = userInfo.getId();
        UserContext.setUserId(id);
        String jwt = jwtAuthService.generateToken(id, ip);

        // redis store
        RBucket<String> rBucket = redissonClient.getBucket(RedisConstant.AUTH_ID_JWT + ":" + id);
        rBucket.set(jwt, 7200, TimeUnit.SECONDS);// 7200s = 2h

        log.info("User login, nick name: {}, jwt token: {}", userInfo.getNickName(), jwt);
        return new UserLoginDto(userInfo.getNickName(), jwt);
    }

    @Override
    public void logout() {
        Long userId = UserContext.getUserId();
        if (userId == null || userId <= 0) {
            log.warn("Got wrong user ID {} from header X-User-Id.", userId);
            return;
        }
        RBucket<String> rBucket = redissonClient.getBucket(RedisConstant.AUTH_ID_JWT + ":" + userId);
        rBucket.delete();
        log.info("Redis deleted auth of user {}.", userId);
    }

    @Override
    public List<UserAddressDto> getUserAddressListByUserId(Long userId) {
        LambdaQueryWrapper<UserAddress> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(UserAddress::getUserId, userId);
        List<UserAddress> userAddressList = userAddressMapper.selectList(lambdaQueryWrapper);
        return userConverter.userAddressPoList2DtoList(userAddressList);
    }
}
