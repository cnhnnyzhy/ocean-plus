package com.ocean.ddd.assembler;

import com.ocean.ddd.dto.rsp.TagRsp;
import com.ocean.ddd.repository.dataobject.TagDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Description: DataObjectToRspAssembler
 * @Author: yang.zhang
 * @Date: 2022/7/13 14:35
 */
@Mapper
public interface DataObjectToRspAssembler {
    DataObjectToRspAssembler INSTANCE = Mappers.getMapper(DataObjectToRspAssembler.class);

    TagRsp toTagRsp(TagDO DO);
}
