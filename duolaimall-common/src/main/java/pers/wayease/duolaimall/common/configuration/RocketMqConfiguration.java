package pers.wayease.duolaimall.common.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.common.configuration
 * @name RocketMqConfiguration
 * @description RocketMQ configuration class.
 * @since 2024-10-14 14:10
 */
@Configuration
@Slf4j
public class RocketMqConfiguration {

//    @Deprecated
//    @Value("${rocketmq.name-server}")
//    String nameServer;
    @Value("${rocketmq.proxy}")
    String proxyEndpoint;

    @Value("${custom.info.project}")
    String projectName;

    @Bean
    public ClientServiceProvider clientServiceProvider() {
        return ClientServiceProvider.loadService();
    }

    @Bean
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.newBuilder()
                .setEndpoints(proxyEndpoint)
                .setNamespace(projectName)
                .build();
    }
}
