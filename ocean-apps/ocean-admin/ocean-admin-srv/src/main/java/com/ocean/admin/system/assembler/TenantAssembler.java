package com.ocean.admin.system.assembler;

import com.ocean.admin.api.dto.rsp.TenantInfoRsp;
import com.ocean.admin.system.entity.SysTenant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 租户assembler
 *
 * @author ocean
 * @date 2022/10/15
 */
@Mapper
public interface TenantAssembler {
    TenantAssembler INSTANCE = Mappers.getMapper(TenantAssembler.class);

    TenantInfoRsp toTenantInfoRsp(SysTenant entity);
}
