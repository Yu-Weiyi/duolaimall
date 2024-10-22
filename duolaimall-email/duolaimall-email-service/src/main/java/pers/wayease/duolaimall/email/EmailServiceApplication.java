package pers.wayease.duolaimall.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.email
 * @name EmailServiceApplication
 * @description Email service application main class.
 * @since 2024-10-05 20:58
 */
@SpringBootApplication
@ComponentScan(basePackages = "pers.wayease.duolaimall")
@EnableAspectJAutoProxy
@EnableFeignClients
@Slf4j
public class EmailServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailServiceApplication.class, args);
        log.info("Email service started successfully.");
    }
}