package com.ocean.client.factory;

import com.ocean.admin.api.feign.ClientDetailsRemoteClient;
import com.ocean.admin.api.feign.TenantRemoteClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 远程客户端工厂类
 *
 * @author ocean
 * @date 2022/10/15
 */
@Getter
@RequiredArgsConstructor
@Component
public class RemoteClientFactory {
    private final TenantRemoteClient tenantRemoteClient;

    private final ClientDetailsRemoteClient clientDetailsRemoteClient;
}
