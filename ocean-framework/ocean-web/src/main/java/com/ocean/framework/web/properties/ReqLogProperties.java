package com.ocean.framework.web.properties;

import com.ocean.framework.core.constant.Constants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

/**
 * 请求日志配置
 *
 * @author yongzheng.xu
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = Constants.PROJECT + ".log.req")
public class ReqLogProperties {
    /**
     * 是否开启请求响应日志日志
     */
    private Boolean enabled = false;
    /**
     * 根据路径，排除不需要记录日志的 路由
     */
    private List<String> excludeUrls = Collections.emptyList();

    private Header header;
    private Rsp rsp;


    public static class Header {
        /**
         * 是否开启头部信息 日志
         */
        private Boolean enabled = false;
        /**
         * 头部信息指定组
         */
        private String keys = "all";

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public String getKeys() {
            return keys;
        }

        public void setKeys(String keys) {
            this.keys = keys;
        }
    }

    public static class Rsp {
        /**
         * 是否开启 响应信息 日志
         */
        private Boolean enabled = false;
        /**
         * 响应信息日志 打印格式,simple|normal
         */
        private String logType = "simple";

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public String getLogType() {
            return logType;
        }

        public void setLogType(String logType) {
            this.logType = logType;
        }
    }
}