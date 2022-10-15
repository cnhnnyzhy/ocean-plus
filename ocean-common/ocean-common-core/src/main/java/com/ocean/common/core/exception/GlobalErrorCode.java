package com.ocean.common.core.exception;

/**
 * @Description: 全局的错误码
 * @Author: yang.zhang
 * @Date: 2022/10/4 21:43
 */
public interface GlobalErrorCode {
    ErrorCode SUCCESS = new ErrorCode(200, "成功");
    ErrorCode FAILED = new ErrorCode(500, "失败");

    ErrorCode BAD_REQUEST = new ErrorCode(400, "请求不正确");
    ErrorCode UNAUTHORIZED = new ErrorCode(401, "账号未登录");
    ErrorCode FORBIDDEN = new ErrorCode(403, "没有该操作权限");
    ErrorCode NOT_FOUND = new ErrorCode(404, "请求未找到");
    ErrorCode METHOD_NOT_ALLOWED = new ErrorCode(405, "请求方法不正确");

    ErrorCode PARAMS_ERROR = new ErrorCode(600, "参数错误");

}
