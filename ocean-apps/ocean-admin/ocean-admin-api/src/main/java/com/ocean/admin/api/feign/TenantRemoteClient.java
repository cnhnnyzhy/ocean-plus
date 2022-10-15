package com.ocean.admin.api.feign;

import com.ocean.admin.api.dto.rsp.TenantInfoRsp;
import com.ocean.common.core.constant.SecurityConstants;
import com.ocean.common.core.constant.ServiceNameConstant;
import com.ocean.common.core.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * 租户远程客户端
 *
 * @author ocean
 * @date 2022/10/15
 */
@FeignClient(contextId = "tenantRemoteClient", value = ServiceNameConstant.ADMIN_SERVICE, path = "/system/tenant")
public interface TenantRemoteClient {
    /**
     * 查询全部有效的租户信息
     *
     * @param from
     * @return
     */
    @GetMapping("list")
    Result<List<TenantInfoRsp>> list(@RequestHeader(SecurityConstants.FROM) String from);
}
