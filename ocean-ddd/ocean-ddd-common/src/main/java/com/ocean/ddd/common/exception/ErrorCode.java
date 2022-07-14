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
    private final Integer code;
    /**
     * 错误提示
     */
    private final String msg;

    public ErrorCode(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }
}
