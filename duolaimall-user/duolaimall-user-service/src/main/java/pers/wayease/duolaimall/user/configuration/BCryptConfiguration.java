package pers.wayease.duolaimall.user.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.user.configuration
 * @name BCryptConfiguration
 * @description BCrypt configuration class.
 * @since 2024-10-13 10:33
 */
@Configuration
@Slf4j
public class BCryptConfiguration {

    private int LOG_ROUNDS = 10;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        log.info("BCryptPasswordEncoder registered, log rounds: {}.", LOG_ROUNDS);
        return new BCryptPasswordEncoder(LOG_ROUNDS);
    }
}
