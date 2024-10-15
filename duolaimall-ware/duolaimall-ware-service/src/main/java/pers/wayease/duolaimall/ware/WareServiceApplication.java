package pers.wayease.duolaimall.ware;

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
 * @package pers.wayease.duolaimall.ware
 * @name WareServiceApplication
 * @description Ware service application main class.
 * @since 2024-10-15 17:40
 */
@SpringBootApplication
@ComponentScan(basePackages = "pers.wayease.duolaimall")
@MapperScan("pers.wayease.duolaimall.ware.mapper")
@EnableAspectJAutoProxy
@EnableFeignClients
@Slf4j
public class WareServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WareServiceApplication.class, args);
        log.info("Ware service started successfully.");
    }
}
