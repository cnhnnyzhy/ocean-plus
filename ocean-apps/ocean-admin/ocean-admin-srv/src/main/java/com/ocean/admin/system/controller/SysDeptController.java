package com.ocean.admin.system.controller;

import com.ocean.admin.system.entity.SysDept;
import com.ocean.common.core.dto.PageQuery;
import com.ocean.common.core.dto.PageVO;
import com.ocean.common.core.dto.Result;
import com.ocean.web.controller.BaseAdminController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

import static com.ocean.admin.constant.ApiPath.ADMIN_GET_DEPT_LIST;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author ocean
 * @since 2022-10-05
 */
@Validated
@RestController
public class SysDeptController extends BaseAdminController {
    @GetMapping(ADMIN_GET_DEPT_LIST)
    public Result<PageVO<SysDept>> getSysDeptList(@RequestBody(required = false) @NotNull PageQuery pageQuery) {
        return Result.success(new PageVO<>());
    }
}
