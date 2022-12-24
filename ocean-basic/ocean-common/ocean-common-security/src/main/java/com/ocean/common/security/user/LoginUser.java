package com.ocean.common.security.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 登录用户信息
 *
 * @author ocean
 * @date 2022/10/15
 */
public class LoginUser extends User {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    /**
     * 用户ID
     */
    @Getter
    private Long id;

    /**
     * 部门ID
     */
    @Getter
    private Long deptId;

    /**
     * 手机号
     */
    @Getter
    private String phone;

    /**
     * 头像
     */
    @Getter
    private String avatar;

    /**
     * 租户ID
     */
    @Getter
    private Long tenantId;

    /**
     * 拓展字段:昵称
     */
    @Getter
    private String nickname;

    /**
     * 拓展字段:姓名
     */
    @Getter
    private String name;

    /**
     * 拓展字段:邮箱
     */
    @Getter
    private String email;

    @JsonCreator
    public LoginUser(@JsonProperty("id") Long id, @JsonProperty("username") String username,
                     @JsonProperty("deptId") Long deptId, @JsonProperty("phone") String phone,
                     @JsonProperty("avatar") String avatar, @JsonProperty("nickname") String nickname,
                     @JsonProperty("name") String name, @JsonProperty("email") String email,
                     @JsonProperty("tenantId") Long tenantId, @JsonProperty("password") String password,
                     @JsonProperty("enabled") boolean enabled, @JsonProperty("accountNonExpired") boolean accountNonExpired,
                     @JsonProperty("credentialsNonExpired") boolean credentialsNonExpired,
                     @JsonProperty("accountNonLocked") boolean accountNonLocked,
                     @JsonProperty("authorities") Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.deptId = deptId;
        this.phone = phone;
        this.avatar = avatar;
        this.tenantId = tenantId;
        this.nickname = nickname;
        this.name = name;
        this.email = email;
    }
}
