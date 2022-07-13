package com.ocean.ddd.common.exception;

import lombok.EqualsAndHashCode;

/**
 * @Description: 业务异常
 * @Author: yang.zhang
 * @Date: 2022/7/12 16:18
 */
@EqualsAndHashCode(callSuper = true)
public final class BizException extends RuntimeException {

    /**
     * 业务错误码
     *
     * @see ErrorCode
     */
    private Integer code;
    /**
     * 错误提示
     */
    private String message;

    /**
     * 有时候返回特定的Code码, 同时需要给出一个返回值, 方便前段 信息格式化
     */
    private Object data;

    /**
     * 空构造方法，避免反序列化问题
     */
    public BizException() {
    }

    public BizException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMsg();
        this.data = null;
    }

    public BizException(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public BizException(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public BizException setCode(Integer code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public BizException setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
