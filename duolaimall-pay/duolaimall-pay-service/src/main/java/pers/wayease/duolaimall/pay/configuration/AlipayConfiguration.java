package pers.wayease.duolaimall.pay.configuration;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 为伊WaYease <a href="mailto:yu_weiyi@outlook.com">yu_weiyi@outlook.com</a>
 * @version 1.0
 * @project duolaimall
 * @package pers.wayease.duolaimall.pay.configuration
 * @name AlipayConfiguration
 * @description Alipay configuration class.
 * @since 2024-10-19 06:11
 */
@Configuration
@ConfigurationProperties(prefix = "alipay.config")
@Data
public class AlipayConfiguration {

    private String appId;
    private String alipayUrl;
    private String appPrivateKey;
    private String alipayPublicKey;

    public static String FORMAT = "json";
    public static final String CHARSET = "UTF-8";
    public static final String SIGN_TYPE = "RSA2";

    @Bean
    public AlipayClient alipayClient() throws AlipayApiException {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl(alipayUrl);
        alipayConfig.setAppId(appId);
        alipayConfig.setPrivateKey(appPrivateKey);
        alipayConfig.setFormat(FORMAT);
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setCharset(CHARSET);
        alipayConfig.setSignType(SIGN_TYPE);

        DefaultAlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        return alipayClient;
    }
}
