package com.ocean.common.dto;

/**
 * Response to caller
 *
 * @author fulan.zjf 2017年10月21日 下午8:53:17
 */
public class Response<T> extends DTO {

    private static final long serialVersionUID = 1L;

    public static final String SUCCESS_CODE = "000000";

    private boolean success;

    private String code;

    private String message;

    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response [success=" + success + ", code=" + code + ", message=" + message + "]";
    }

    public static <T> Response buildSuccess(T data) {
        Response response = new Response();
        response.setSuccess(true);
        response.setCode(SUCCESS_CODE);
        response.setData(data);
        return response;
    }

    public static Response buildFailure(String code, String message) {
        Response response = new Response();
        response.setSuccess(false);
        response.setCode(code);
        response.setMessage(message);
        return response;
    }
}
