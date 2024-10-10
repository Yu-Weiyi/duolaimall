package pers.wayease.duolaimall.product;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product
 * @name ProductServiceApplication
 * @description Product service application main class.
 * @since 2024-10-06 19:23
 */
@SpringBootApplication
@ComponentScan(basePackages = "pers.wayease.duolaimall")
@MapperScan("pers.wayease.duolaimall.product.mapper")
@Slf4j
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
        log.info("Product service started successfully.");
    }
}

// TODO redis cache AOP
// TODO react speed AOP
// TODO add custom banner with project info, svc info, pod info and so on
// TODO transactional check
// TODO admin operation audit AOP
// TODO add prometheus metrics
