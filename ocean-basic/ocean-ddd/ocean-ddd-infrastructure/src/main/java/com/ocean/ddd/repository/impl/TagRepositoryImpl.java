package com.ocean.ddd.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ocean.ddd.assembler.EntityToDataObjectConvertor;
import com.ocean.ddd.domain.entity.Tag;
import com.ocean.ddd.domain.valueobject.TagId;
import com.ocean.ddd.repository.TagRepository;
import com.ocean.ddd.repository.dataobject.TagDO;
import com.ocean.ddd.repository.mapper.TagMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Description: TagRepository实现类
 * @Author: yang.zhang
 * @Date: 2022/7/12 14:33
 */
@Repository
public class TagRepositoryImpl extends ServiceImpl<TagMapper, TagDO> implements TagRepository {
    @Override
    public Tag saveEntity(Tag entity) {
        TagDO DO = EntityToDataObjectConvertor.toTagDO(entity);
        Long id = DO.getId();
        LocalDateTime now = LocalDateTime.now();
        DO.setUpdatedAt(now);
        if (Objects.isNull(id)) {
            DO.setCreatedAt(now);
            DO.setType(2);
            DO.setVisibleScope(1);
            DO.setOperationType(1);
            DO.setCreatedBy(1L);
            DO.setUpdatedBy(1L);
            DO.setValueType(1);
            DO.setUseTarget(1);
            DO.setBusinessUid(1L);
            DO.setBizId(2);
            DO.setBizApplicationId("bcd");
            this.save(DO);
            entity.setId(new TagId(DO.getId()));
        } else {
            this.updateById(DO);
        }
        return entity;
    }

    @Override
    public int deleteEntity(Tag entity) {
        return 0;
    }
}
