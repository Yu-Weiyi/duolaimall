package pers.wayease.duolaimall.search;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.search
 * @name SearchServiceApplication
 * @description Search service application main class.
 * @since 2024-10-10 10:18
 */
@SpringBootApplication
@ComponentScan(basePackages = "pers.wayease.duolaimall")
@EnableFeignClients
@Slf4j
public class SearchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchServiceApplication.class, args);
        log.info("Search service started successfully.");
    }
}