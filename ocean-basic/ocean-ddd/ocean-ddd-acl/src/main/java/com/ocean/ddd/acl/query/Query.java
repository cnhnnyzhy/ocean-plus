package com.ocean.ddd.acl.query;

import com.ocean.ddd.dto.req.PageReq;
import com.ocean.ddd.dto.rsp.PageRsp;

/**
 * @Description:
 * @Author: yang.zhang
 * @Date: 2022/7/12 14:53
 */
public interface Query<T> {
    /**
     * 根据id查询数据
     *
     * @param id
     * @return
     */
    T queryById(Long id);

//    /**
//     * 根据条件查询一条数据
//     *
//     * @return
//     */
//    T queryOne();
//
//    /**
//     * 查询列表
//     *
//     * @return
//     */
//    List<T> queryList();

    /**
     * 分页查询
     *
     * @param req
     * @return
     */
    PageRsp<T> queryPage(PageReq req);
}
