package com.ocean.admin.api.dto.req.cmd;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ocean.common.dto.Cmd;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 租户添加命令
 *
 * @author ocean
 * @date 2022/12/25
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TenantAddCmd extends Cmd {

    @ApiModelProperty(name = "租户名称")
    @NotBlank
    private String name;

    @ApiModelProperty(name = "租户编码")
    @NotBlank
    private String code;

    @ApiModelProperty(name = "租户域名")
    @NotBlank
    private String domain;

    @ApiModelProperty(name = "操作人id")
    @NotNull
    private Long operatorId;
}
