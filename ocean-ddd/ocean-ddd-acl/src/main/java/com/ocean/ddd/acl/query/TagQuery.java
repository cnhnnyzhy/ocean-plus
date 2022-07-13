package com.ocean.ddd.acl.query;

import com.ocean.ddd.dto.req.qry.TagGetListQry;
import com.ocean.ddd.dto.rsp.TagRsp;

import java.util.List;

/**
 * @Description: 标签查询服务
 * @Author: yang.zhang
 * @Date: 2022/7/12 14:45
 */
public interface TagQuery extends Query<TagRsp> {
    List<TagRsp> queryList(TagGetListQry req);
}
