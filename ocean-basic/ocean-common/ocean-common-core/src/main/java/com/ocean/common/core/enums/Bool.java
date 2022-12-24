package com.ocean.common.core.enums;

import com.ocean.common.core.exception.BizException;
import com.ocean.common.core.exception.GlobalErrorCode;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description: Bool类型枚举类
 * @Author: ocean
 * @Date: 2022/4/19 15:40
 */
@Getter
public enum Bool {
    FALSE(false, 0),
    TRUE(true, 1);

    private Boolean code;
    private Integer value;


    Bool(Boolean code, Integer value) {
        this.code = code;
        this.value = value;
    }

    public boolean isTrue() {
        return code;
    }

    private static final Map<Boolean, Bool> CODE_MAP = Arrays.stream(Bool.values()).collect(Collectors.toMap(Bool::getCode, (bool) -> bool));
    private static final Map<Integer, Bool> VALUE_MAP = Arrays.stream(Bool.values()).collect(Collectors.toMap(Bool::getValue, (bool) -> bool));

    public static Bool getByCode(Boolean code) {
        if (code == null) {
            return null;
        }
        return CODE_MAP.get(code);
    }

    public static Bool getByValue(Integer value) {
        return getByValue(value, null);
    }

    public static Bool getByValue(Integer value, Bool defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        Optional<Bool> optional = Optional.ofNullable(VALUE_MAP.get(value));
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new BizException(GlobalErrorCode.PARAMS_ERROR.getCode(), String.format(GlobalErrorCode.INVALID_ENUM_VALUE_MESSAGE, Bool.class.getSimpleName(), value));
    }
}
