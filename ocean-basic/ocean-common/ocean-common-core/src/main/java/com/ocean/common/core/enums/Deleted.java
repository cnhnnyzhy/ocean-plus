package com.ocean.common.core.enums;

import com.ocean.common.core.exception.BizException;
import com.ocean.common.core.exception.GlobalErrorCode;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description: 删除状态枚举类
 * @Author: ocean
 * @Date: 2022/4/19 15:40
 */
@Getter
public enum Deleted {
    /**
     * 未删除
     */
    N(0),
    /**
     * 已删除
     */
    Y(1);


    private Integer value;


    Deleted(Integer value) {
        this.value = value;
    }


    private static final Map<Integer, Deleted> VALUE_MAP = Arrays.stream(Deleted.values()).collect(Collectors.toMap(Deleted::getValue, (bool) -> bool));

    public static final String ENUM_VALUES = "0,1";
    public static final String ENUM_DESC = "是否删除：0-否（默认值）；1-是；";

    public static Deleted getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        Optional<Deleted> optional = Optional.ofNullable(VALUE_MAP.get(value));
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new BizException(GlobalErrorCode.PARAMS_ERROR.getCode(), String.format(GlobalErrorCode.INVALID_ENUM_VALUE_MESSAGE, Deleted.class.getSimpleName(), value));
    }
}
