package com.ocean.common.security.util;

import com.ocean.common.security.user.LoginUser;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 安全工具类
 *
 * @author ocean
 * @date 2022/10/15
 */
@UtilityClass
public class SecurityUtils {

    /**
     * 获取Authentication
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户
     */
    public LoginUser getLoginUser() {
        Authentication authentication = getAuthentication();
        return getLoginUser(authentication);
    }

    /**
     * 获取登录用户信息
     *
     * @param authentication
     * @return
     */
    public LoginUser getLoginUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof LoginUser) {
            return (LoginUser) principal;
        }
        return null;
    }


}
