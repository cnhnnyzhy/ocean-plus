package com.ocean.admin.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ocean.admin.api.dto.rsp.TenantInfoRsp;
import com.ocean.admin.api.enums.TenantStatus;
import com.ocean.admin.system.assembler.TenantAssembler;
import com.ocean.admin.system.entity.SysTenant;
import com.ocean.admin.system.repository.SysTenantRepository;
import com.ocean.admin.system.service.SysTenantService;
import com.ocean.common.core.enums.Deleted;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 租户服务实现类
 *
 * @author ocean
 * @date 2022/10/15
 */
@RequiredArgsConstructor
@Service
@DS("slave")
public class SysTenantServiceImpl implements SysTenantService {
    private final SysTenantRepository sysTenantRepository;

    @Override
    public List<TenantInfoRsp> getNormalTenantList() {
        return Optional.ofNullable(
                sysTenantRepository.list(new LambdaQueryWrapper<SysTenant>()
                        .eq(SysTenant::getStatus, TenantStatus.NORMAL.getValue())
                        .eq(SysTenant::getIsDeleted, Deleted.N.getValue())
                )).orElse(new ArrayList<>(0)).stream().map(TenantAssembler.INSTANCE::toTenantInfoRsp).collect(Collectors.toList());
    }
}
