package com.ocean.admin.system.repository.impl;

import com.ocean.admin.system.entity.SysUser;
import com.ocean.admin.system.mapper.SysUserMapper;
import com.ocean.admin.system.repository.SysUserRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author ocean
 * @since 2022-10-05
 */
@Service
public class SysUserRepositoryImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserRepository {

}
