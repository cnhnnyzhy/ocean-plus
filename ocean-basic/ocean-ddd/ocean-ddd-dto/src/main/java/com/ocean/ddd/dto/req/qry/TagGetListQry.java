package com.ocean.ddd.dto.req.qry;

import com.ocean.ddd.dto.Qry;
import lombok.Data;

/**
 * @Description: 查询标签列表参数
 * @Author: yang.zhang
 * @Date: 2022/7/13 14:27
 */
@Data
public class TagGetListQry extends Qry {
    private String name;
}
