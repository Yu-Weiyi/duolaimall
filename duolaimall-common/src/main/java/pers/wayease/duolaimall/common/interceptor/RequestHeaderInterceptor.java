package pers.wayease.duolaimall.common.interceptor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import pers.wayease.duolaimall.common.constant.HeaderConstant;
import pers.wayease.duolaimall.common.constant.RedisConstant;
import pers.wayease.duolaimall.common.context.UserContext;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Date;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.interceptor
 * @name RequestHeaderInterceptor
 * @description Request header interceptor class.
 * @since 2024-10-13 15:03
 */
@Component
@Slf4j
public class RequestHeaderInterceptor implements HandlerInterceptor {

    private final String SUBJECT = "USER_AUTH";
    private final String AUDIENCE = "website";
    private final String ISSUER = "duolaimall";
    private final int EXPIRATION_TIME = 7200;// 7200s = 2h

    private final String CUSTOM_ID_KEY = "userId";
    private final String CUSTOM_IP_KEY = "loginIp";

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        log.info("Request Header interceptor: enter.");
        if (!(handler instanceof HandlerMethod)) {
            log.info("Request Header interceptor: skip.");
            return true;
        }

        // userId

        // FIXME error in istio ext auth
        Long userId =2L;
        String userIdHeader = httpServletRequest.getHeader(HeaderConstant.USER_ID_HEADER);
        if (userIdHeader == null || StringUtils.isBlank(userIdHeader)) {
            log.warn("Request Header interceptor: wrong userId.");

            // temp fix
            String jwtToken = httpServletRequest.getHeader(HeaderConstant.TOKEN_HEADER);
            if (jwtToken == null || StringUtils.isBlank(jwtToken)) {
                userId = 0L;
            } else {
                log.info("Validate token: {}", jwtToken);

                try {
                    SecretKey hmacShaSecretKey = Keys.hmacShaKeyFor(secretKey.getBytes());

                    // verify JWT
                    Claims claims = Jwts.parser()
                            .setSigningKey(hmacShaSecretKey)
                            .build()
                            .parseClaimsJws(jwtToken)
                            .getBody();

                    // verify subject
                    if (!claims.getSubject().equals(SUBJECT)) {
                        log.info("JWT token validation failed for wrong subject: {}", claims.getSubject());
                        return false;
                    }

                    // verify issuer
                    if (!claims.getIssuer().equals(ISSUER)) {
                        log.info("JWT token validation failed for wrong issuer: {}", claims.getIssuer());
                        return false;
                    }

                    // verify time
                    if (claims.getExpiration().before(Date.from(Instant.now()))) {
                        log.info("JWT token validation failed for wrong expiration: {}", claims.getExpiration());
                        return false;
                    }

                    // verify user id in redis
                    Long tempUserId = Long.valueOf((String) claims.get(CUSTOM_ID_KEY));
                    RBucket<String> rBucket = redissonClient.getBucket(RedisConstant.AUTH_ID_JWT + ":" + userId);
                    String storedJwtToken = rBucket.get();
                    if (storedJwtToken == null || StringUtils.isBlank(storedJwtToken) || !storedJwtToken.equals(jwtToken)) {
                        log.info("JWT token validation failed for wrong stored token: {}", storedJwtToken);
                        return false;
                    }

                    // verified
                    userId = tempUserId;

                } catch (Exception e) {
                    log.error("JWT token validation failed, JWT: {}", jwtToken, e);
                    return false;
                }
            }
        } else {
            userId = Long.valueOf(userIdHeader);
        }

        UserContext.setUserId(userId);

        // userTempId
        String userTempId = httpServletRequest.getHeader(HeaderConstant.USER_TEMP_ID_HEADER);
        UserContext.setUserTempId(userTempId);

        log.info("JWT auth interceptor: pass.");
        return true;
    }
}
