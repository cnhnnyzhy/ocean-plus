package com.ocean.framework.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ocean.framework.core.exception.BizException;
import com.ocean.framework.core.exception.ErrorCode;
import com.ocean.framework.core.exception.GlobalErrorCode;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.MDC;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Description: 接口统一响应结果
 * @Author: yang.zhang
 * @Date: 2022/7/12 15:38
 */
@Getter
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1475147362408907882L;
    /**
     * 错误码
     *
     * @see ErrorCode#getCode()
     */
    @Setter
    private String code;
    /**
     * 错误提示，用户可阅读
     *
     * @see ErrorCode#getMessage()
     */
    @Setter
    private String message;

    @JsonProperty("trace_id")
    private String traceId = MDC.get("traceId");
    /**
     * 返回数据
     */
    @Setter
    private T data;

    /**
     * 将传入的 result 对象，转换成另外一个泛型结果的对象
     * <p>
     * 因为 A 方法返回的 RspResult 对象，不满足调用其的 B 方法的返回，所以需要进行转换。
     *
     * @param result 传入的 result 对象
     * @param <T>    返回的泛型
     * @return 新的 RspResult 对象
     */
    public static <T> Result<T> error(Result<?> result) {
        return error(result.getCode(), result.getMessage());
    }

    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.code = GlobalErrorCode.FAILED.getCode();
        result.message = message;
        return result;
    }

    public static <T> Result<T> error(String code, String message) {
        if (GlobalErrorCode.SUCCESS.getCode().equals(code)) {
            throw new IllegalArgumentException("code 必须是错误的！");
        }
        Result<T> result = new Result<>();
        result.code = code;
        result.message = message;
        return result;
    }

    public static <T> Result<T> error(ErrorCode code) {
        return error(code.getCode(), code.getMessage());
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = GlobalErrorCode.SUCCESS.getCode();
        result.message = "success";
        result.data = data;
        return result;
    }

    public static <T> Result<T> of(String code, String message, T data) {
        Result<T> result = new Result<>();
        result.code = code;
        result.message = message;
        result.data = data;
        return result;
    }

    public static boolean isSuccess(String code) {
        return Objects.equals(code, GlobalErrorCode.SUCCESS.getCode());
    }

    @JsonIgnore
    public boolean isSuccess() {
        return isSuccess(code);
    }

    @JsonIgnore
    public boolean isError() {
        return !isSuccess();
    }

    // ========= 和 Exception 异常体系集成 =========

    /**
     * 判断是否有异常。如果有，则抛出 {@link BizException} 异常
     */
    public void checkError() throws BizException {
        if (isSuccess()) {
            return;
        }
        // 业务异常
        throw new BizException(code, message);
    }

    public static <T> Result<T> error(BizException exception) {
        return error(exception.getCode(), exception.getMessage());
    }
}
