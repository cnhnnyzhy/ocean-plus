package com.ocean.ddd.assembler;

import com.ocean.ddd.module.domain.entity.Tag;
import com.ocean.ddd.dto.rsp.TagRsp;

import java.util.Optional;

/**
 * @Description: EntityToRspConvertor
 * @Author: yang.zhang
 * @Date: 2022/7/12 16:59
 */
public final class EntityToRspConvertor {
    private EntityToRspConvertor() {
    }

    public static TagRsp toTagRsp(Tag entity) {
        if (entity == null) {
            return null;
        }
        TagRsp rsp = new TagRsp();
        Optional.ofNullable(entity.getId()).ifPresent(obj -> rsp.setId(obj.getValue()));
        Optional.ofNullable(entity.getName()).ifPresent(obj -> rsp.setName(obj.getValue()));
        return rsp;
    }
}
