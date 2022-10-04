package com.ocean.admin.system.repository.impl;

import com.ocean.admin.system.entity.SysUserRule;
import com.ocean.admin.system.mapper.SysUserRuleMapper;
import com.ocean.admin.system.repository.SysUserRuleRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户与角色关联表 服务实现类
 * </p>
 *
 * @author ocean
 * @since 2022-10-05
 */
@Service
public class SysUserRuleRepositoryImpl extends ServiceImpl<SysUserRuleMapper, SysUserRule> implements SysUserRuleRepository {

}
