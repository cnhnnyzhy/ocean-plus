package com.ocean.admin.system.controller;

import com.ocean.admin.system.dto.query.PageQuerySysDeptVO;
import com.ocean.admin.system.dto.vo.SysDeptVO;
import com.ocean.framework.core.dto.PageVO;
import com.ocean.framework.core.dto.Result;
import com.ocean.framework.web.controller.BaseAdminController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping(ADMIN_GET_DEPT_LIST)
    public Result<PageVO<SysDeptVO>> getSysDeptList(@RequestBody PageQuerySysDeptVO pageQuerySysDeptVO) {
        return Result.success(new PageVO<>());
    }
}
