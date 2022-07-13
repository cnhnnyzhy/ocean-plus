package com.ocean.ddd.assembler;

import com.ocean.ddd.domain.entity.Tag;
import com.ocean.ddd.domain.valueobject.TagName;
import com.ocean.ddd.dto.req.cmd.TagAddCmd;

/**
 * @Description: ReqToEntityConvertor
 * @Author: yang.zhang
 * @Date: 2022/7/12 16:59
 */
public final class ReqToEntityConvertor {
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
}
