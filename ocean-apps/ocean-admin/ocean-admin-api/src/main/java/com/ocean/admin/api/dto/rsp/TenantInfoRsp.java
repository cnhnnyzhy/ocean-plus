package com.ocean.admin.api.dto.rsp;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ocean.common.core.dto.Rsp;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 租户信息DTO
 *
 * @author ocean
 * @date 2022/10/15
 */
@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TenantInfoRsp extends Rsp {

    private static final long serialVersionUID = 722496126087556599L;
    /**
     * 租户id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 域名
     */
    private String domain;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 是否删除：0-否 1-是
     */
    private String isDeleted;
}
