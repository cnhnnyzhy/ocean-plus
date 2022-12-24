package com.ocean.common.core.exception;

import lombok.Getter;

/**
 * @Description: 错误码对象
 * @Author: yang.zhang
 * @Date: 2022/10/4 21:39
 */
@Getter
public class ErrorCode {
    private Integer code;
    private String msg;

    public ErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
