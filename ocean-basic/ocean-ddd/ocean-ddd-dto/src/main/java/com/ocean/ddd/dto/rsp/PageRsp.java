package com.ocean.ddd.dto.rsp;

import com.ocean.ddd.dto.Rsp;
import lombok.Data;

import java.util.List;

/**
 * @Description: 分页返回DTO
 * @Author: yang.zhang
 * @Date: 2022/7/12 15:13
 */
@Data
public class PageRsp<T> extends Rsp {
    /**
     * 当前页
     */
    private Integer pageNo;
    /**
     * 每页大小
     */
    private Integer pageSize;
    /**
     * 总条数
     */
    private Long total;
    /**
     * 记录列表
     */
    private List<T> records;
}
