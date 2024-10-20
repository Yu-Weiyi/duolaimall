package pers.wayease.duolaimall.pay;

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
 * @package pers.wayease.duolaimall.pay
 * @name PayServiceApplication
 * @description Pay service application main class.
 * @since 2024-10-18 20:21
 */
@SpringBootApplication
@ComponentScan(basePackages = "pers.wayease.duolaimall")
@MapperScan("pers.wayease.duolaimall.pay.mapper")
@EnableAspectJAutoProxy
@EnableFeignClients
@Slf4j
public class PayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayServiceApplication.class, args);
        log.info("Pay service started successfully.");
    }
}
