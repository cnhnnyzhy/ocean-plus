package com.ocean.admin.system.dto.vo;

import com.ocean.framework.core.dto.VO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 部门VO
 *
 * @author ocean
 * @date 2024/5/1
 */
@Data
public class SysDeptVO extends VO {
    @ApiModelProperty(value = "部门ID")
    private Long id;
}
