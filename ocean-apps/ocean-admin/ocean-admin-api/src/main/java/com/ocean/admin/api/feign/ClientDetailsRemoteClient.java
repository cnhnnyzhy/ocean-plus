package com.ocean.admin.api.feign;

import com.ocean.admin.api.dto.rsp.OauthClientDetailsRsp;
import com.ocean.common.core.constant.SecurityConstants;
import com.ocean.common.core.constant.ServiceNameConstant;
import com.ocean.common.core.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * 客户端信息远程调用客户端
 *
 * @author ocean
 * @date 2022/10/15
 */
@FeignClient(contextId = "clientDetailsRemoteClient", value = ServiceNameConstant.ADMIN_SERVICE, path = "/system/client")
public interface ClientDetailsRemoteClient {
    /**
     * 通过clientId 查询客户端信息
     *
     * @param clientId
     * @param from
     * @return
     */
    @GetMapping("/getClientDetailsById/{clientId}")
    Result<OauthClientDetailsRsp> getClientDetailsById(@PathVariable("clientId") String clientId,
                                                       @RequestHeader(SecurityConstants.FROM) String from);

    /**
     * 查询全部客户端
     *
     * @param from
     * @return
     */
    @GetMapping("list")
    Result<List<OauthClientDetailsRsp>> listClientDetails(@RequestHeader(SecurityConstants.FROM) String from);
}
