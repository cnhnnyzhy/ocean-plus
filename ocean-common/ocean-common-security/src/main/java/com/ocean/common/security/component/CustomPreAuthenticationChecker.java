package com.ocean.common.security.component;

import com.ocean.common.security.util.SecurityMessageSourceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

/**
 * 自定义checker
 *
 * @author ocean
 * @date 2022/11/26
 */
@Slf4j
public class CustomPreAuthenticationChecker implements UserDetailsChecker {
    private MessageSourceAccessor messageSourceAccessor = SecurityMessageSourceUtils.getAccessor();

    @Override
    public void check(UserDetails user) {
        if (!user.isAccountNonLocked()) {
            log.debug("User account is locked");
            throw new LockedException(messageSourceAccessor.getMessage("AbstractUserDetailsAuthenticationProvider.locked", "User account is locked"));
        }
        if (!user.isEnabled()) {
            log.debug("User account is disabled");
            throw new DisabledException(messageSourceAccessor.getMessage("AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"));
        }

        if (!user.isAccountNonExpired()) {
            log.debug("User account is expired");
            throw new AccountExpiredException(messageSourceAccessor.getMessage("AbstractUserDetailsAuthenticationProvider.expired", "User account has expired"));
        }
    }
}
