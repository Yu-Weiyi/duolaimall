package pers.wayease.duolaimall.promo.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.promo.configuration
 * @name CronTaskConfiguration
 * @description Cron task configuration class.
 * @since 2024-10-21 06:43
 */
@Configuration
@Slf4j
public class CronTaskConfiguration {

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(32);
        scheduler.setThreadNamePrefix("PromoCacheTaskScheduler-");
        scheduler.initialize();
        return scheduler;
    }
}
