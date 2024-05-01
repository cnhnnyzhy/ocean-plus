package com.ocean.admin.api.dto.rsp;

import com.ocean.framework.core.dto.DTO;
import lombok.Data;

/**
 * 用户信息
 *
 * @author ocean
 * @date 2022/11/15
 */
@Data
public class SysUserInfoRsp extends DTO {
    /**
     * 用户基本信息
     */
    private SysUserRsp sysUser;
    /**
     * 权限标识集合
     */
    private String[] permissions;
    /**
     * 角色集合
     */
    private Long[] roles;
}
