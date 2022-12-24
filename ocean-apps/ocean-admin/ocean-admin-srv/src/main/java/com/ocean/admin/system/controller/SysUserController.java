package com.ocean.admin.system.controller;

import com.ocean.admin.api.dto.rsp.SysUserInfoRsp;
import com.ocean.common.core.dto.Result;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author ocean
 * @since 2022-10-05
 */
@RequiredArgsConstructor
@Api(value = "user", tags = "用户管理")
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    @GetMapping("/info/{username}")
    public Result<SysUserInfoRsp> info(@PathVariable String username) {
        return Result.success(null);
    }
}
