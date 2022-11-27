package com.ocean.common.core.constant;

import static com.ocean.common.core.constant.Constants.PREFIX;

/**
 * 认证授权相关常量
 *
 * @author ocean
 * @date 2022/10/15
 */
public interface SecurityConstants {
    /**
     * 标志
     */
    String FROM = "from";

    /**
     * 内部
     */
    String FROM_IN = "Y";

    /**
     * 角色前缀
     */
    String ROLE = "ROLE_";

    /**
     * {bcrypt} 加密的特征码
     */
    String BCRYPT = "{bcrypt}";

    /**
     * token 相关前缀
     */
    String TOKEN_PREFIX = PREFIX + "token:";

    /**
     * oauth 相关前缀
     */
    String OAUTH_PREFIX = PREFIX + "oauth:";

    /**
     * 授权码模式code key 前缀
     */
    String OAUTH_CODE_PREFIX = OAUTH_PREFIX + "code:";

    /**
     * 客户端模式
     */
    String CLIENT_CREDENTIALS = "client_credentials";

    /**
     * 客户端编号
     */
    String CLIENT_ID = "client_id";

    /**
     * 用户ID字段
     */
    String DETAILS_USER_ID = "user_id";

    /**
     * 用户名
     */
    String DETAILS_USERNAME = "username";

    /**
     * 协议字段
     */
    String DETAILS_LICENSE = "license";

    /**
     * 激活字段 兼容外围系统接入
     */
    String ACTIVE = "active";

    /**
     * AES 加密
     */
    String AES = "aes";

    /**
     * 项目的license
     */
    String LICENSE = "made by ocean";
}
