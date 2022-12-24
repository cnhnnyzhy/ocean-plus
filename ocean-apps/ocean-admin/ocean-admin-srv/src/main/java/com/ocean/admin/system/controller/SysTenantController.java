package com.ocean.admin.system.controller;

import com.ocean.admin.api.dto.rsp.TenantInfoRsp;
import com.ocean.admin.system.service.SysTenantService;
import com.ocean.common.core.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 租户表 前端控制器
 * </p>
 *
 * @author ocean
 * @since 2022-10-05
 */
@Api
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/tenant")
public class SysTenantController {
    private final SysTenantService sysTenantService;

    /**
     * 查询全部有效的租户
     *
     * @return
     */
    @ApiOperation(value = "查询全部有效的租户列表接口", tags = "租户管理")
    @GetMapping("list")
    public Result<List<TenantInfoRsp>> list() {
        return Result.success(sysTenantService.getNormalTenantList());
    }
}
