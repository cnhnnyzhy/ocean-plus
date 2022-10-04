package com.ocean.admin.system.repository.impl;

import com.ocean.admin.system.entity.SysTenant;
import com.ocean.admin.system.mapper.SysTenantMapper;
import com.ocean.admin.system.repository.SysTenantRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 租户表 服务实现类
 * </p>
 *
 * @author ocean
 * @since 2022-10-05
 */
@Service
public class SysTenantRepositoryImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements SysTenantRepository {

}
