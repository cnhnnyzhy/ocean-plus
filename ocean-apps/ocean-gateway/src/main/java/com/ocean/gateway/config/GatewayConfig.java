package com.ocean.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 网关配置信息
 *
 * @author ocean
 * @date 2023/1/9
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "gateway")
public class GatewayConfig {

    /**
     * 网关解密登录前端密码 秘钥 {@link com.pig4cloud.pigx.gateway.filter.PasswordDecoderFilter}
     */
    private String encodeKey;
}
