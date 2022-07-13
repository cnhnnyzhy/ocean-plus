package com.ocean.ddd.repository;

/**
 * @Description: 抽象的Repository接口
 * @Author: yang.zhang
 * @Date: 2022/7/12 14:55
 */
public interface Repository<T> {
    /**
     * 保存方法
     *
     * @param entity
     * @return
     */
    T saveEntity(T entity);

    /**
     * 删除方法
     *
     * @param entity
     * @return 删除行数
     */
    int deleteEntity(T entity);
}
