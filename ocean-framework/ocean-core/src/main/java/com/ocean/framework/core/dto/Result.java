package com.ocean.framework.core.dto;

import com.ocean.framework.core.exception.ErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.slf4j.MDC;

import java.io.Serializable;

import static com.ocean.framework.core.exception.GlobalErrorCode.SUCCESS;

/**
 * @Description: 接口响应结果
 * @Author: yang.zhang
 * @Date: 2022/10/4 21:35
 */
@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {
    /**
     * 是否成功
     */
    @ApiModelProperty(value = "接口调用是否成功：true|false")
    private boolean success;
    /**
     * 响应码
     */
    @ApiModelProperty(value = "接口响应码")
    private Integer code;
    /**
     * 响应信息
     */
    @ApiModelProperty(value = "接口响应信息")
    private String msg;
    /**
     * 响应数据
     */
    @ApiModelProperty(value = "接口响应数据")
    private T data;
    /**
     * 调用链路trace_id
     */
    @ApiModelProperty(value = "接口调用TraceId")
    private String traceId = MDC.get("traceId");

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setCode(SUCCESS.getCode());
        result.setMsg(SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(Integer code, String msg) {
        Result<T> result = new Result<>();
        result.setSuccess(SUCCESS.getCode().equals(code) ? true : false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> Result<T> error(ErrorCode errorCode, String msg) {
        return error(errorCode.getCode(), msg);
    }
}
