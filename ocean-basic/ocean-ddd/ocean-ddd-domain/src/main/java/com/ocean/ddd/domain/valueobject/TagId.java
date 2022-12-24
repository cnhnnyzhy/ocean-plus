package com.ocean.ddd.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 标签id
 * @Author: yang.zhang
 * @Date: 2022/7/12 17:03
 */
@AllArgsConstructor
@Getter
public class TagId extends ValueObject {
    private Long value;
}
