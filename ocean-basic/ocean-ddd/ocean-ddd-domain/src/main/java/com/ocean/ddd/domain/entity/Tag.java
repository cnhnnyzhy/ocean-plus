package com.ocean.ddd.domain.entity;

import com.ocean.ddd.domain.valueobject.TagId;
import com.ocean.ddd.domain.valueobject.TagName;
import lombok.Data;

/**
 * @Description: Tag实体
 * @Author: yang.zhang
 * @Date: 2022/7/12 14:00
 */
@Data
public class Tag extends Entity {
    private TagId id;
    private TagName name;
}
