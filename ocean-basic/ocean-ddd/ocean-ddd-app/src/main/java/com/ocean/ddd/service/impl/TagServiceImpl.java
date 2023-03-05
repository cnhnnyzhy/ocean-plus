package com.ocean.ddd.service.impl;

import com.ocean.ddd.assembler.EntityToRspConvertor;
import com.ocean.ddd.assembler.ReqToEntityConvertor;
import com.ocean.ddd.dto.req.cmd.TagAddCmd;
import com.ocean.ddd.dto.req.qry.TagGetListQry;
import com.ocean.ddd.dto.rsp.TagRsp;
import com.ocean.ddd.factory.QueryFactory;
import com.ocean.ddd.factory.RepositoryFactory;
import com.ocean.ddd.service.TagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 标签服务
 * @Author: yang.zhang
 * @Date: 2022/7/12 16:50
 */
@Service
public class TagServiceImpl implements TagService {
    @Resource
    private RepositoryFactory repositoryFactory;
    @Resource
    private QueryFactory queryFactory;

    @Override
    public TagRsp add(TagAddCmd req) {
        return EntityToRspConvertor.toTagRsp(repositoryFactory.getTagRepository().saveEntity(ReqToEntityConvertor.toTag(req)));
    }

    @Override
    public List<TagRsp> getList(TagGetListQry req) {
        return queryFactory.getTagQuery().queryList(req);
    }
}
