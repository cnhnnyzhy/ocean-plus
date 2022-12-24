package com.ocean.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * Token服务
 *
 * @author ocean
 * @date 2022/10/16
 */
public interface TokenService {
    /**
     * 删除请求token并刷新
     *
     * @param token
     * @return
     */
    Boolean removeToken(String token);

    /**
     * 根据用户名分页查询token
     *
     * @param page
     * @param username
     * @return
     */
    Page queryTokenByUsername(Page page, String username);

    /**
     * 分页查询token
     *
     * @param page
     * @return
     */
    Page queryToken(Page page);

    /**
     * 查询token信息
     *
     * @param token
     * @return
     */
    OAuth2AccessToken queryTokenInfo(String token);

}
