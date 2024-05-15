package com.ocean.admin.system.controller;

import com.ocean.admin.system.dto.query.GetSysDeptListQuery;
import com.ocean.admin.system.dto.vo.SysDeptVO;
import com.ocean.framework.core.dto.PageVO;
import com.ocean.framework.core.dto.Result;
import com.ocean.framework.web.controller.BaseAdminController;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.ocean.admin.constant.ApiPath.ADMIN_DEPT_LIST;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author ocean
 * @since 2022-10-05
 */
@Api("部门")
@Validated
@RestController
public class SysDeptController extends BaseAdminController {

    /**
     * 获取部门列表
     *
     * @param getSysDeptListQuery
     * @return
     */
    @PostMapping(ADMIN_DEPT_LIST)
    public Result<PageVO<SysDeptVO>> getSysDeptList(@RequestBody GetSysDeptListQuery getSysDeptListQuery) {
        getSysDeptListQuery.buildPage();
        if (true)
            throw new RuntimeException("error");
        return Result.success(new PageVO<>());
    }
}
