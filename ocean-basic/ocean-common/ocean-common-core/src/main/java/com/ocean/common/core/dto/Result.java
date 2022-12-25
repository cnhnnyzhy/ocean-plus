package com.ocean.common.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ocean.common.core.exception.ErrorCode;
import com.ocean.common.core.exception.GlobalErrorCode;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: 接口响应结果
 * @Author: yang.zhang
 * @Date: 2022/10/4 21:35
 */
@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {
    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String msg;
    /**
     * 响应数据
     */
    private T data;
    /**
     * 调用链路trace_id
     */
    @JsonProperty("trace_id")
    private String traceId;

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(GlobalErrorCode.SUCCESS.getCode());
        result.setMsg(GlobalErrorCode.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(Integer code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> error(ErrorCode errorCode) {
        Result<T> result = new Result<>();
        result.setCode(errorCode.getCode());
        result.setMsg(errorCode.getMsg());
        return result;
    }

    public static <T> Result<T> error(ErrorCode errorCode, String msg) {
        Result<T> result = new Result<>();
        result.setCode(errorCode.getCode());
        result.setMsg(msg);
        return result;
    }
}
