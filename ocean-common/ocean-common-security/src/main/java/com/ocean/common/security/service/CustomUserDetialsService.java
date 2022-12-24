package com.ocean.common.security.service;

import cn.hutool.core.util.ArrayUtil;
import com.ocean.admin.api.dto.rsp.SysUserInfoRsp;
import com.ocean.admin.api.dto.rsp.SysUserRsp;
import com.ocean.common.core.constant.Constants;
import com.ocean.common.core.constant.SecurityConstants;
import com.ocean.common.core.dto.Result;
import com.ocean.common.core.util.ResultOptional;
import com.ocean.common.security.user.LoginUser;
import org.springframework.core.Ordered;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义UserDetailsService
 *
 * @author ocean
 * @date 2022/11/15
 */
public interface CustomUserDetialsService extends UserDetailsService, Ordered {
    /**
     * 是否支持此客户端校验
     *
     * @param clientId  请求客户端
     * @param grantType 授权类型
     * @return true/false
     */
    default boolean support(String clientId, String grantType) {
        return true;
    }

    /**
     * 排序值 默认取最大的
     *
     * @return 排序值
     */
    default int getOrder() {
        return 0;
    }

    /**
     * 构建userdetails
     *
     * @param result 用户信息
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    default UserDetails getUserDetails(Result<SysUserInfoRsp> result) {
        // @formatter:off
        return ResultOptional.of(result)
                .getData()
                .map(this::convertUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        // @formatter:on
    }

    /**
     * UserInfo 转 UserDetails
     *
     * @param info
     * @return 返回UserDetails对象
     */
    default UserDetails convertUserDetails(SysUserInfoRsp info) {
        Set<String> dbAuthsSet = new HashSet<>();
        if (ArrayUtil.isNotEmpty(info.getRoles())) {
            // 获取角色
            Arrays.stream(info.getRoles()).forEach(roleId -> dbAuthsSet.add(SecurityConstants.ROLE + roleId));
            // 获取资源
            dbAuthsSet.addAll(Arrays.asList(info.getPermissions()));

        }
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils
                .createAuthorityList(dbAuthsSet.toArray(new String[0]));
        SysUserRsp user = info.getSysUser();
        // 构造security用户

        return new LoginUser(user.getId(), user.getUserName(), user.getDeptId(), user.getMobile(), user.getAvatar(),
                user.getNickName(), user.getName(), user.getEmail(), user.getTenantId(),
                SecurityConstants.BCRYPT + user.getPassword(), true, true, true,
                !Constants.STATUS_LOCK.equals(user.getIsLocked()), authorities);
    }

    /**
     * 通过用户实体查询
     *
     * @param loginUser user
     * @return
     */
    default UserDetails loadUserByUser(LoginUser loginUser) {
        return this.loadUserByUsername(loginUser.getUsername());
    }
}
