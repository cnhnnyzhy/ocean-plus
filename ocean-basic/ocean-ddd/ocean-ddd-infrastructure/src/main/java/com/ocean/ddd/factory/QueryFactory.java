package com.ocean.ddd.factory;

import com.ocean.ddd.acl.query.TagQuery;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description: 查询服务工厂类
 * @Author: yang.zhang
 * @Date: 2022/7/13 14:38
 */
@Getter
@Component
public class QueryFactory {
    @Resource
    private TagQuery tagQuery;
}
