package com.ocean.common.core.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @Description: 业务异常
 * @Author: yang.zhang
 * @Date: 2022/10/4 22:00
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class BizException extends RuntimeException {
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 异常出现时的关键数据
     */
    private String data;


    public BizException(Integer code, String message, String data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BizException(Integer code, String message) {
        this(code, message, "");
    }


    public BizException(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMsg());
    }


    public BizException(ErrorCode errorCode, String message) {
        this(errorCode.getCode(), (message != null && !"".equals(message)) ? message : errorCode.getMsg());
    }

    public BizException(ErrorCode errorCode, String message, String data) {
        this(errorCode.getCode(), (message != null && !"".equals(message)) ? message : errorCode.getMsg(), data);
    }

    public BizException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
}
