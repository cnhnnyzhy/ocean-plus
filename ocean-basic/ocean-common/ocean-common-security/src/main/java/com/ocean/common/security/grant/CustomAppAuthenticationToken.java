package com.ocean.common.security.grant;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 自定义认证token
 *
 * @author ocean
 * @date 2022/10/22
 */
public class CustomAppAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;
    private String code;
    @Getter
    private String grantType;

    public CustomAppAuthenticationToken(String mobile, String code, String grantType) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.principal = mobile;
        this.code = code;
        this.grantType = grantType;
    }

    public CustomAppAuthenticationToken(UserDetails user) {
        super(user.getAuthorities());
        this.principal = user;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.code;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
