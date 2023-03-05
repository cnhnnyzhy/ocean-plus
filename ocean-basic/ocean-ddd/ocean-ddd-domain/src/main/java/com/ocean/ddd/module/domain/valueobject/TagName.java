package com.ocean.ddd.module.domain.valueobject;

import com.ocean.ddd.common.exception.Exceptions;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description: 标签名称
 * @Author: yang.zhang
 * @Date: 2022/7/12 17:03
 */
@Getter
public class TagName extends ValueObject {
    private String value;

    public TagName(String value) {
        this.value = value;
        validate();
    }

    protected void validate() {
        if (StringUtils.containsAny(this.value, "<", ">", "/", "\\")) {
            throw Exceptions.generateParamErrorException("参数[name]不合法");
        }
    }
}
