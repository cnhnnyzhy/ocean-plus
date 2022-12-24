package com.ocean.ddd.common.exception;

import com.ocean.ddd.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * @Description: 全局的异常处理类
 * @Author: yang.zhang
 * @Date: 2022/7/12 16:31
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionAdvice {
    private static final String EXCEPTION_PREFIX = "Exception";

    @ResponseBody
    @ExceptionHandler(BizException.class)
    public Result<ErrorBody> bizException(HttpServletRequest request, BizException e) {
        if (!GlobalErrorCode.PARAM_ERROR.getCode().equals(e.getCode())) {
            log.error(EXCEPTION_PREFIX, e);
        }
        return Result.error(e.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public Result<ErrorBody> validationException(HttpServletRequest request, BindException e) {
        String defaultMessage = e.getAllErrors().get(0).getDefaultMessage();
        // TODO 错误码逻辑仍需优化
        // 在这里判断是类型转换异常还是校验异常
        assert defaultMessage != null;
        if (defaultMessage.contains(EXCEPTION_PREFIX)) {
            FieldError objectError = (FieldError) e.getAllErrors().get(0);
            String field = objectError.getField();
            String fieldNew = field.replaceAll("[A-Z]", "_$0").toLowerCase();
            defaultMessage = fieldNew + "参数格式错误";
        }
        return Result.error(GlobalErrorCode.PARAM_ERROR.getCode(), defaultMessage);
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public Result<ErrorBody> validationNumException(HttpServletRequest request, MethodArgumentTypeMismatchException e) {
        log.error(EXCEPTION_PREFIX, e);
        String fieldNew = e.getName().replaceAll("[A-Z]", "_$0").toLowerCase();
        return Result.error(GlobalErrorCode.PARAM_ERROR.getCode(), fieldNew + "参数格式错误！");
    }

    @ResponseBody
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result<ErrorBody> argumentException(HttpServletRequest request, IllegalArgumentException e) {
        log.error(EXCEPTION_PREFIX, e);
        return Result.error(GlobalErrorCode.PARAM_ERROR.getCode(), "参数错误: " + e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public Result<ErrorBody> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        log.error(EXCEPTION_PREFIX, e);
        String msg = "请求参数不正确：" + " " + e.getParameterName();
        return Result.error(GlobalErrorCode.PARAM_ERROR.getCode(), msg);
    }


    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result<ErrorBody> constraintViolationExceptionHandler(ConstraintViolationException e) {
        log.error(EXCEPTION_PREFIX, e);
        String msg = "请求参数不正确：" + " " + e.getMessage();
        return Result.error(GlobalErrorCode.PARAM_ERROR.getCode(), msg);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result<ErrorBody> systemException(HttpServletRequest request, Exception e) {
        log.error(EXCEPTION_PREFIX, e);
        return Result.error(GlobalErrorCode.FAILED);
    }
}
