package com.ocean.admin.system.repository.impl;

import com.ocean.admin.system.entity.SysUserPost;
import com.ocean.admin.system.mapper.SysUserPostMapper;
import com.ocean.admin.system.repository.SysUserPostRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户与岗位关联表 服务实现类
 * </p>
 *
 * @author ocean
 * @since 2022-10-05
 */
@Service
public class SysUserPostRepositoryImpl extends ServiceImpl<SysUserPostMapper, SysUserPost> implements SysUserPostRepository {

}
