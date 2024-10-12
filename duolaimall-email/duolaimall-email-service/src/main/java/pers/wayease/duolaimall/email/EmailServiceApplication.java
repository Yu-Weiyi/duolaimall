package pers.wayease.duolaimall.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
@Slf4j
public class EmailServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailServiceApplication.class, args);
        log.info("Email service started successfully.");
    }
}

// TODO send right now for one or 5 minutes later for all