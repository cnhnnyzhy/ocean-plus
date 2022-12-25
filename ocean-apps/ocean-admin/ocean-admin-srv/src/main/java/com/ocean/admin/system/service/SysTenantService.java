package com.ocean.admin.system.service;

import com.ocean.admin.api.dto.req.cmd.TenantAddCmd;
import com.ocean.admin.api.dto.rsp.TenantInfoRsp;

import java.util.List;

/**
 * 租户服务
 *
 * @author ocean
 * @date 2022/10/15
 */
public interface SysTenantService {
    /**
     * 获取全部有效的租户
     *
     * @return
     */
    List<TenantInfoRsp> getNormalTenantList();

    /**
     * 新增租户
     *
     * @param req
     */
    void add(TenantAddCmd req);

    void add2(TenantAddCmd req);

}
