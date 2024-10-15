package pers.wayease.duolaimall.cart.configuration;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.MasterSlaveServersConfig;
import org.redisson.config.ReadMode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.cart.configuration
 * @name RedissonConfiguration
 * @description Redisson configuration class.
 * @since 2024-10-10 14:41
 */
@Configuration
@ConfigurationProperties("spring.redis")
@Data
public class RedissonConfiguration {

    private String master;
    private String replicas;
    private String password;

    private int timeout = 3000;
    private int pingConnectionInterval = 60000;
    private int connectionPoolSize = 32;
    private int connectionMinimumIdleSize = 10;
    private ReadMode readMode = ReadMode.SLAVE;

    // Use master-slave mode
    @Bean
    RedissonClient redissonClient() {
        Config config = new Config();
        MasterSlaveServersConfig masterSlaveServersConfig = config
                .setCodec(new JsonJacksonCodec())
                .useMasterSlaveServers()
                .setMasterAddress(master)
                .addSlaveAddress(replicas)
                .setPassword(password)
                .setTimeout(timeout)
                .setPingConnectionInterval(pingConnectionInterval)
                .setMasterConnectionPoolSize(connectionPoolSize)
                .setSlaveConnectionPoolSize(connectionPoolSize)
                .setMasterConnectionMinimumIdleSize(connectionMinimumIdleSize)
                .setSlaveConnectionMinimumIdleSize(connectionMinimumIdleSize)
                .setReadMode(readMode);
        return Redisson.create(config);
    }
}
