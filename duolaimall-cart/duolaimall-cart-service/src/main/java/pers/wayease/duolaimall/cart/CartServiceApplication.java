package pers.wayease.duolaimall.cart;

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
 * @package pers.wayease.duolaimall.cart
 * @name CartServiceApplication
 * @description Cart service application main class.
 * @since 2024-10-14 10:09
 */
@SpringBootApplication
@ComponentScan(basePackages = "pers.wayease.duolaimall")
@MapperScan("pers.wayease.duolaimall.cart.mapper")
@EnableAspectJAutoProxy
@EnableFeignClients
@Slf4j
public class CartServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartServiceApplication.class, args);
        log.info("Cart service started successfully.");
    }
}
