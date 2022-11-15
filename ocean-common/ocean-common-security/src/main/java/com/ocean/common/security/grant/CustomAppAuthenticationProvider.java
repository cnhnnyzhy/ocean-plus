package com.ocean.common.security.grant;

import com.ocean.common.core.util.SpringContextHolder;
import com.ocean.common.security.service.CustomUserDetialsService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

/**
 * @author ocean
 * @since 2022-11-15
 */
@Slf4j
public class CustomAppAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    /**
     * user 属性校验
     */
    @Setter
    private UserDetailsChecker preAuthenticationChecks = new AccountStatusUserDetailsChecker();

    /**
     * 校验 请求信息userDetails
     *
     * @param userDetails    用户信息
     * @param authentication 认证信息
     * @throws AuthenticationException
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        if (authentication.getCredentials() == null) {
            this.logger.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }

    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (authentication.getCredentials() == null) {
            log.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException("Bad credentials");
        }

        CustomAppAuthenticationToken requestToken = (CustomAppAuthenticationToken) authentication;
        // 此处已获得 客户端认证 获取对应 userDetailsService
        Authentication clientAuthentication = SecurityContextHolder.getContext().getAuthentication();
        String clientId = clientAuthentication.getName();

        Map<String, CustomUserDetialsService> userDetailsServiceMap = SpringContextHolder
                .getBeansOfType(CustomUserDetialsService.class);
        Optional<CustomUserDetialsService> optional = userDetailsServiceMap.values().stream()
                .filter(service -> service.support(clientId, requestToken.getGrantType()))
                .max(Comparator.comparingInt(Ordered::getOrder));

        if (!optional.isPresent()) {
            throw new InternalAuthenticationServiceException("UserDetailsService error , not register");
        }

        // 手机号
        String phone = authentication.getName();
        UserDetails userDetails = optional.get().loadUserByUsername(phone);

        // userDeails 校验
        preAuthenticationChecks.check(userDetails);

        CustomAppAuthenticationToken token = new CustomAppAuthenticationToken(userDetails);
        token.setDetails(authentication.getDetails());
        return token;
    }

    @Override
    protected UserDetails retrieveUser(String phone, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(CustomAppAuthenticationToken.class);
    }

}