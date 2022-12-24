package com.ocean.admin.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ocean.admin.api.dto.rsp.SysUserRsp;
import com.ocean.admin.system.entity.SysUser;
import com.ocean.admin.system.repository.SysRoleRepository;
import com.ocean.admin.system.repository.SysUserRepository;
import com.ocean.admin.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 系统用户服务实现
 *
 * @author ocean
 * @date 2022/11/30
 */
@RequiredArgsConstructor
@Service
public class SysUserServiceImpl implements SysUserService {
    private final SysUserRepository sysUserRepository;
    private final SysRoleRepository sysRoleRepository;

    @Override
    public SysUserRsp queryUserInfo(String username) {
        Assert.notBlank(username, "username不能为空");
        SysUser sysUser = sysUserRepository.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserName, username));
        Assert.notNull(sysUser, "根据用户名未查询到用户数据");
        return null;
    }
}
