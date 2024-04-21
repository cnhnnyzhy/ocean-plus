package com.ocean.common.dto;

/**
 * Response with single record to return
 * <p/>
 * Created by Danny.Lee on 2017/11/1.
 */
public class SingleResponse<T> extends Response {

    private static final long serialVersionUID = 1L;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static SingleResponse buildSuccess() {
        SingleResponse response = new SingleResponse();
        response.setSuccess(true);
        return response;
    }

    public static SingleResponse buildFailure(String code, String message) {
        return (SingleResponse) Response.buildFailure(code, message);
    }

    public static <T> SingleResponse<T> of(T data) {
        SingleResponse<T> response = new SingleResponse<>();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

}
