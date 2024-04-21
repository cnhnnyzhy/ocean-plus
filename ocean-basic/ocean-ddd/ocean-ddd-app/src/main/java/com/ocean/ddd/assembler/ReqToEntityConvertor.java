package com.ocean.ddd.assembler;

import com.ocean.ddd.dto.req.cmd.TagAddCmd;
import com.ocean.ddd.module.domain.entity.Tag;
import com.ocean.ddd.module.domain.valueobject.TagName;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Description: ReqToEntityConvertor
 * @Author: yang.zhang
 * @Date: 2022/7/12 16:59
 */
public final class ReqToEntityConvertor {
    public static final ReqToEntityAssembler ASSEMBLER = Mappers.getMapper(ReqToEntityAssembler.class);

    private ReqToEntityConvertor() {
    }

    public static Tag toTag(TagAddCmd req) {
        if (req == null) {
            return null;
        }
        Tag entity = new Tag();
        entity.setName(new TagName(req.getName()));
        return entity;
    }

    @Mapper
    public interface ReqToEntityAssembler {
    }
}
