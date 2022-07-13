package com.ocean.ddd.acl.query.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocean.ddd.acl.query.TagQuery;
import com.ocean.ddd.assembler.DataObjectToRspAssembler;
import com.ocean.ddd.dto.req.PageReq;
import com.ocean.ddd.dto.req.qry.TagGetListQry;
import com.ocean.ddd.dto.rsp.PageRsp;
import com.ocean.ddd.dto.rsp.TagRsp;
import com.ocean.ddd.repository.dataobject.TagDO;
import com.ocean.ddd.repository.mapper.TagMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: Tag查询服务实现类
 * @Author: yang.zhang
 * @Date: 2022/7/12 15:23
 */
@Component
public class TagQueryImpl extends ServiceImpl<TagMapper, TagDO> implements TagQuery {
    @Override
    public TagRsp queryById(Long id) {
        return null;
    }

    @Override
    public List<TagRsp> queryList(TagGetListQry req) {
        return this.list(new LambdaQueryWrapper<TagDO>().like(StringUtils.isNotBlank(req.getName()), TagDO::getName, req.getName()).last("limit 10"))
                .stream().map(DataObjectToRspAssembler.INSTANCE::toTagRsp).collect(Collectors.toList());
    }

    @Override
    public PageRsp<TagRsp> queryPage(PageReq req) {
        return null;
    }
}
