package com.ocean.ddd.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Description: ReqToEntityAssembler
 * @Author: yang.zhang
 * @Date: 2022/7/12 16:57
 */
@Mapper
public class ReqToEntityAssembler {
    public static final ReqToEntityAssembler INSTANCE = Mappers.getMapper(ReqToEntityAssembler.class);
}
