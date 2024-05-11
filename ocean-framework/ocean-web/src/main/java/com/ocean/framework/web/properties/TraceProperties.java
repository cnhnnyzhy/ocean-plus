package com.ocean.framework.web.properties;

import com.ocean.framework.core.constant.Constants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 日志链路追踪配置
 *
 * @author zlt
 * @since 2019/8/13
 */
@Setter
@Getter
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = Constants.PROJECT + ".log.trace")
public class TraceProperties {
    /**
     * 是否开启日志链路追踪
     */
    private Boolean enabled = false;

    /**
     * 响应结果, 记录方式
     */
    private String keys = "x-traceId-header";
}