package com.ocean.ddd.common.exception;

/**
 * @Description: 全局错误码
 * @Author: yang.zhang
 * @Date: 2022/7/12 16:10
 */
public interface GlobalErrorCode {
    ErrorCode SUCCESS = new ErrorCode(200, "成功");
    ErrorCode FAILED = new ErrorCode(500, "失败");
    ErrorCode PARAM_ERROR = new ErrorCode(600, "参数错误");
    ErrorCode REMOTE_CALL_ERROR = new ErrorCode(700, "远程调用错误");

}
