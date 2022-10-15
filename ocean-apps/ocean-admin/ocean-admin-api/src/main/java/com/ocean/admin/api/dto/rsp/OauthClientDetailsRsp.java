package com.ocean.admin.api.dto.rsp;

import com.ocean.common.core.dto.Rsp;
import lombok.Data;

/**
 * Oauth客户端信息DTO
 *
 * @author ocean
 * @date 2022/10/16
 */
@Data
public class OauthClientDetailsRsp extends Rsp {
    private static final long serialVersionUID = 8046194361622683714L;

    private Long id;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 资源ID
     */
    private String resourceIds;

    /**
     * 作用域
     */
    private String scope;

    /**
     * 授权方式[A,B,C]
     */
    private String[] authorizedGrantTypes;

    /**
     * 回调地址
     */
    private String webServerRedirectUri;

    /**
     * 权限
     */
    private String authorities;

    /**
     * 请求令牌有效时间
     */
    private Integer accessTokenValidity;

    /**
     * 刷新令牌有效时间
     */
    private Integer refreshTokenValidity;

    /**
     * 扩展信息
     */
    private String additionalInformation;

    /**
     * 是否自动放行
     */
    private String autoapprove;

    /**
     * 删除标记
     */
    private String delFlag;
}
