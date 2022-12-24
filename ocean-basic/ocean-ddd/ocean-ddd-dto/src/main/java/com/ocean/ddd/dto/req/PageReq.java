package com.ocean.ddd.dto.req;

import com.ocean.ddd.dto.Cmd;
import lombok.Data;

/**
 * @Description: 分页查询请求参数
 * @Author: yang.zhang
 * @Date: 2022/7/12 15:07
 */
@Data
public abstract class PageReq extends Cmd {
    /**
     * 当前页
     */
    private Integer pageNo;
    /**
     * 每页大小
     */
    private Integer pageSize;
    /**
     * 需要统计总条数
     */
    private boolean needCount;
    /**
     * 需要查询出记录列表
     */
    private boolean needRecords;
}
