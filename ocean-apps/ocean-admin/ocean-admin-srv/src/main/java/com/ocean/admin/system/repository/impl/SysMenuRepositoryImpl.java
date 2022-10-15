package com.ocean.admin.system.repository.impl;

import com.ocean.admin.system.entity.SysMenu;
import com.ocean.admin.system.mapper.SysMenuMapper;
import com.ocean.admin.system.repository.SysMenuRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author ocean
 * @since 2022-10-05
 */
@Service
public class SysMenuRepositoryImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuRepository {

}
