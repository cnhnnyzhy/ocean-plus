package com.ocean.admin.api.dto.req.qry;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ocean.framework.core.dto.Command;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 查询租户请求dto
 *
 * @author ocean
 * @date 2022/12/24
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class QueryTenantQuery extends Command {

    private Integer isDeleted;


    private String isDeleted2;

    private Integer isDeleted3;

    @NotBlank(message = "参数不能为空")
    private String isDeleted4;
}
