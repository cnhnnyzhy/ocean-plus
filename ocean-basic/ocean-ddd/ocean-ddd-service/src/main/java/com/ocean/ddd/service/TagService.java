package com.ocean.ddd.service;

import com.ocean.ddd.dto.req.cmd.TagAddCmd;
import com.ocean.ddd.dto.req.qry.TagGetListQry;
import com.ocean.ddd.dto.rsp.TagRsp;

import java.util.List;

/**
 * @Description: Tag服务
 * @Author: yang.zhang
 * @Date: 2022/7/12 15:28
 */
public interface TagService {
    /**
     * 添加标签
     *
     * @param req
     * @return
     */
    TagRsp add(TagAddCmd req);

    /**
     * 获取标签列表
     *
     * @param req
     * @return
     */
    List<TagRsp> getList(TagGetListQry req);
}
