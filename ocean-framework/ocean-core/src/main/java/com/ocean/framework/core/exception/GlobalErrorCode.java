package com.ocean.framework.core.exception;

/**
 * @Description: 全局错误码
 * @Author: yang.zhang
 * @Date: 2022/7/12 16:10
 */
public interface GlobalErrorCode {
    ErrorCode SUCCESS = ErrorCode.of("200", "成功");
    ErrorCode FAILED = ErrorCode.of("500", "失败");
    ErrorCode PARAM_ERROR = ErrorCode.of("600", "参数错误");
    ErrorCode REMOTE_CALL_ERROR = ErrorCode.of("700", "远程调用错误");
    ErrorCode DB_ERROR = ErrorCode.of("800", "数据库错误");
    ErrorCode NO_LOGIN = ErrorCode.of("900", "未登录");
    ErrorCode NO_PERMISSION = ErrorCode.of("901", "无权限");

}
