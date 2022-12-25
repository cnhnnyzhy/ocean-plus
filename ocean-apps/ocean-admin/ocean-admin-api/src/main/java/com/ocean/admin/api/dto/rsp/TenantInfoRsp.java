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
     * 生效时间
     */
    private LocalDateTime validTime;

    /**
     * 失效时间
     */
    private LocalDateTime expireTime;

    /**
     * 状态：1-正常；2-锁定
     */
    private Integer status;

    /**
     * 是否删除：0-否；1-是
     */
    private Integer isDeleted;

}
