package pers.wayease.duolaimall.user.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pers.wayease.duolaimall.common.constant.RedisConstant;
import pers.wayease.duolaimall.user.service.JwtAuthService;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.user.service.impl
 * @name JwtAuthServiceImpl
 * @description JWT auth service implement class.
 * @since 2024-10-13 14:10
 */
@Service
@Slf4j
public class JwtAuthServiceImpl implements JwtAuthService {

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
    public String generateToken(Long userId, String loginIp) {
        SecretKey hmacShaSecretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        Map<String, String> claims = new HashMap<>();
        claims.put(CUSTOM_ID_KEY, String.valueOf(userId));
        claims.put(CUSTOM_IP_KEY, loginIp);
        Instant now = Instant.now();
        String jwtToken = Jwts.builder()
                .header()
                .add("alg", "HS256")
                .add("typ", "JWT")
                .and()
                .subject(SUBJECT)
                .audience()
                .add(AUDIENCE)
                .and()
                .issuer(ISSUER)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(EXPIRATION_TIME)))
                .claims(claims)
                .signWith(hmacShaSecretKey, Jwts.SIG.HS256)
                .compact();
        log.info("JWT token generated: {}", jwtToken);
        return jwtToken;
    }

    @Override
    public Long validateToken(String jwtToken) {
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
                return 0L;
            }

            // verify issuer
            if (!claims.getIssuer().equals(ISSUER)) {
                log.info("JWT token validation failed for wrong issuer: {}", claims.getIssuer());
                return 0L;
            }

            // verify time
            if (claims.getExpiration().before(Date.from(Instant.now()))) {
                log.info("JWT token validation failed for wrong expiration: {}", claims.getExpiration());
                return 0L;
            }

            // verify user id in redis
            Long userId = (Long) claims.get(CUSTOM_ID_KEY);
            RBucket<String> rBucket = redissonClient.getBucket(RedisConstant.AUTH_ID_JWT + ":" + userId);
            String storedJwtToken = rBucket.get();
            if (storedJwtToken == null || StringUtils.isBlank(storedJwtToken) || !storedJwtToken.equals(jwtToken)) {
                log.info("JWT token validation failed for wrong stored token: {}", storedJwtToken);
                return 0L;
            }

            // verified
            return userId;

        } catch (Exception e) {
            log.info("Invalid JWT token {}", jwtToken);
            return 0L;
        }
    }
}
