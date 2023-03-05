package com.ocean.ddd.module.domain.specification;

/**
 * @Description: 规格
 * @Author: yang.zhang
 * @Date: 2022/7/13 16:08
 */
public interface Specification<T> {
    /**
     * 是否满足条件
     *
     * @param value
     * @return
     */
    boolean isSatisfiedBy(T value);
}
