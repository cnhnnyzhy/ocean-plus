package com.ocean.admin.system.repository.impl;

import com.ocean.admin.system.entity.SysRole;
import com.ocean.admin.system.mapper.SysRoleMapper;
import com.ocean.admin.system.repository.SysRoleRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author ocean
 * @since 2022-10-05
 */
@Service
public class SysRoleRepositoryImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleRepository {

}
