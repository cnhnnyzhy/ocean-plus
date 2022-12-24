package com.ocean.ddd.assembler;

import com.ocean.ddd.domain.entity.Tag;
import com.ocean.ddd.repository.dataobject.TagDO;

import java.util.Optional;

/**
 * @Description: 实体 to DO的转换器
 * @Author: yang.zhang
 * @Date: 2022/7/12 17:39
 */
public final class EntityToDataObjectConvertor {
    private EntityToDataObjectConvertor() {
    }

    public static TagDO toTagDO(Tag entity) {
        if (entity == null) {
            return null;
        }
        TagDO DO = new TagDO();
        Optional.ofNullable(entity.getId()).ifPresent(obj -> DO.setId(obj.getValue()));
        Optional.ofNullable(entity.getName()).ifPresent(obj -> DO.setName(obj.getValue()));
        return DO;
    }
}
