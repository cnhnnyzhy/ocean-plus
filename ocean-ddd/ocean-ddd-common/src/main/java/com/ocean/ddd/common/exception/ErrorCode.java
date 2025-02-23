package com.ocean.ddd.common.exception;

import lombok.Getter;

/**
 * @Description: 错误码对象
 * @Author: yang.zhang
 * @Date: 2022/7/12 16:02
 */
@Getter
public class ErrorCode {
    /**
     * 错误码
     */
    private final String code;
    /**
     * 错误提示
     */
    private final String message;

    public ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorCode of(String code, String message) {
        return new ErrorCode(code, message);
    }
}
