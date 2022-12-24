package com.ocean.admin.system.service;

import com.ocean.admin.api.dto.rsp.SysUserRsp;

/**
 * 系统用户服务
 *
 * @author ocean
 * @date 2022/11/30
 */
public interface SysUserService {
    SysUserRsp queryUserInfo(String username);
}
