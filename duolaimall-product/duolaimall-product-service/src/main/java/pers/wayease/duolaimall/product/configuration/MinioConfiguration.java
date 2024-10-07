package pers.wayease.duolaimall.product.configuration;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.product.configuration
 * @name MinioConfiguration
 * @description Minio configuration class.
 * @since 2024-10-06 19:52
 */
@Configuration
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioConfiguration {

    private String endpointUrl;
    private String accessKey;
    private String secretKey;
    private String bucketName;

    @Bean
    public MinioClient minioClient() {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(endpointUrl)
                .credentials(accessKey, secretKey)
                .build();
        return minioClient;
    }
}
