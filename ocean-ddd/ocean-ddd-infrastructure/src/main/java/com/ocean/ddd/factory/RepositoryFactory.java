package com.ocean.ddd.factory;

import com.ocean.ddd.repository.TagRepository;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description: 仓储工厂类
 * @Author: yang.zhang
 * @Date: 2022/7/12 16:51
 */
@Getter
@Component
public class RepositoryFactory {
    @Resource
    private TagRepository tagRepository;
}
