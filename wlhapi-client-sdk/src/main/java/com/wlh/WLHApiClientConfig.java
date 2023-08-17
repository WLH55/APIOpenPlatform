package com.wlh;

import com.wlh.wlhsdk.client.HttpRequestClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author WLH
 * @verstion 1.0
 */
@Data
@ComponentScan
@Configuration
@ConfigurationProperties(prefix = "wlh.api.client")
public class WLHApiClientConfig {
    private String accessKey;
    private String secretKey;

    @Bean
    public HttpRequestClient wlhApiClient() {
        return new HttpRequestClient(accessKey, secretKey);
    }
}
