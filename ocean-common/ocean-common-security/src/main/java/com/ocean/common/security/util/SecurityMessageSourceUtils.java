package com.ocean.common.security.util;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

/**
 * 异常处理工具类
 *
 * @author ocean
 * @date 2022/10/16
 */
public class SecurityMessageSourceUtils extends ReloadableResourceBundleMessageSource {
    public SecurityMessageSourceUtils() {
        setBasename("classpath:messages/messages");
        setDefaultLocale(Locale.CHINA);
    }

    public static MessageSourceAccessor getAccessor() {
        return new MessageSourceAccessor(new SecurityMessageSourceUtils());
    }
}
