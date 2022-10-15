package com.ocean.common.core.util;

import com.ocean.common.core.dto.Result;

import java.util.Objects;
import java.util.Optional;

/**
 * 结果操作类
 *
 * @author ocean
 * @date 2022/10/16
 */
public class ResultOptional<T> {
    private final Result<T> original;

    public ResultOptional(Result<T> original) {
        this.original = original;
    }

    public static <T> ResultOptional<T> of(Result<T> original) {
        return new ResultOptional<>(Objects.requireNonNull(original));
    }

    public Integer getCode() {
        return original.getCode();
    }

    public Optional<T> getData() {
        return Optional.ofNullable(original.getData());
    }
}
