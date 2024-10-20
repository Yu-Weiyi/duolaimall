package pers.wayease.duolaimall.promo;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.promo
 * @name PromoServiceApplication
 * @description Promo service application main class.
 * @since 2024-10-21 01:57
 */
@SpringBootApplication
@ComponentScan(basePackages = "pers.wayease.duolaimall")
@MapperScan("pers.wayease.duolaimall.promo.mapper")
@EnableAspectJAutoProxy
@EnableFeignClients
@Slf4j
public class PromoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PromoServiceApplication.class, args);
        log.info("Promo service started successfully.");
    }
}
