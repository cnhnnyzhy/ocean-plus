package com.ocean.auth.config;

import com.ocean.common.security.component.CustomAuthenticationProvider;
import com.ocean.common.security.grant.CustomAppAuthenticationProvider;
import com.ocean.common.security.handler.FormAuthenticationFailureHandler;
import com.ocean.common.security.handler.SsoLogoutSuccessHandler;
import com.ocean.common.security.handler.TenantSavedRequestAwareAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * 认证配置
 *
 * @author ocean
 * @date 2022/11/15
 */
@Primary
@Order(90)
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/token/login").loginProcessingUrl("/token/form")
                .successHandler(tenantSavedRequestAwareAuthenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler()).and().logout()
                .logoutSuccessHandler(logoutSuccessHandler()).deleteCookies("JSESSIONID").invalidateHttpSession(true)
                .and().authorizeRequests().antMatchers("/token/**", "/actuator/**", "/mobile/**").permitAll()
                .anyRequest().authenticated().and().csrf().disable();
    }

    /**
     * 自定义 provider 列表注入
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        CustomAuthenticationProvider authenticationProvider = new CustomAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        // 处理默认的密码模式认证
        auth.authenticationProvider(authenticationProvider);
        // 自定义的认证模式
        auth.authenticationProvider(new CustomAppAuthenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/favicon.ico", "/css/**", "/error");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 具备租户传递能力
     *
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler tenantSavedRequestAwareAuthenticationSuccessHandler() {
        return new TenantSavedRequestAwareAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new FormAuthenticationFailureHandler();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new SsoLogoutSuccessHandler();
    }


    /**
     * 密码处理器
     *
     * @return 动态密码处理器 {类型}密文
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
