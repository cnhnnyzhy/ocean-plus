package com.ocean.admin.api.dto.req.qry;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ocean.common.core.enums.Deleted;
import com.ocean.common.core.validator.MatchOne;
import com.ocean.common.core.validator.OneOf;
import com.ocean.common.core.validator.OneOfInt;
import com.ocean.common.dto.Qry;
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
public class QueryTenantQry extends Qry {

    @MatchOne(slice = Deleted.ENUM_VALUES, key = "is_deleted")
    private Integer isDeleted;


    @MatchOne(slice = Deleted.ENUM_VALUES, key = "is_deleted2")
    private String isDeleted2;

    @OneOfInt(value = {0, 1})
    private Integer isDeleted3;

    @NotBlank(message = "参数不能为空")
    @OneOf(value = {"0", "1"})
    private String isDeleted4;
}
