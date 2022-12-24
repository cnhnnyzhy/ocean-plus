package com.ocean.common.core.exception;

import cn.hutool.core.util.StrUtil;
import com.ocean.common.core.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;

/**
 * 全局异常处理
 *
 * @author ocean
 * @date 2021/12/31
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionAdvice {

    private static final String EXCEPTION_PREFIX = "Exception";

    private static final String PARAM_ERROR_MSG_PREFIX = "参数错误：";
    private ObjectError error;

    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public Result<Object> validationException(HttpServletRequest request, BindException e) {
        List<ObjectError> errors = e.getAllErrors();
        ObjectError error = errors.get(0);
        String message = error.getDefaultMessage();
        assert message != null;
        if (message.contains(EXCEPTION_PREFIX)) {
            FieldError objectError = (FieldError) error;
            String field = objectError.getField().replaceAll("[A-Z]", "_$0").toLowerCase();
            message = field + "参数格式错误";
            return Result.error(GlobalErrorCode.PARAMS_ERROR, PARAM_ERROR_MSG_PREFIX + message);
        }
        if (error instanceof FieldError) {
            FieldError fieldError = (FieldError) error;
            String field = StrUtil.toUnderlineCase(fieldError.getField());
            message = "[" + field + "]" + message;
            return Result.error(GlobalErrorCode.PARAMS_ERROR, PARAM_ERROR_MSG_PREFIX + message);
        }
        return Result.error(GlobalErrorCode.PARAMS_ERROR, PARAM_ERROR_MSG_PREFIX + message);
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public Result<Object> validationNumException(HttpServletRequest request, MethodArgumentTypeMismatchException e) {
        String field = e.getName().replaceAll("[A-Z]", "_$0").toLowerCase();
        String message = field + "参数格式错误";
        return Result.error(GlobalErrorCode.PARAMS_ERROR, PARAM_ERROR_MSG_PREFIX + message);
    }

    @ResponseBody
    @ExceptionHandler(value = {IllegalArgumentException.class, ConstraintViolationException.class})
    public Result<Object> argumentException(HttpServletRequest request, Exception e) {
        log.error(EXCEPTION_PREFIX, e);
        return Result.error(GlobalErrorCode.PARAMS_ERROR, PARAM_ERROR_MSG_PREFIX + e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public Result<Object> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        log.error(EXCEPTION_PREFIX, e);
        String message = e.getParameterName();
        return Result.error(GlobalErrorCode.PARAMS_ERROR, PARAM_ERROR_MSG_PREFIX + message);
    }

    @ResponseBody
    @ExceptionHandler(value = BizException.class)
    public Result<Object> bizException(BizException e) {
        if (Objects.isNull(e.getCode()) || e.getCode() != GlobalErrorCode.PARAMS_ERROR.getCode()) {
            if (StringUtils.isNotBlank(e.getData())) {
                log.error(EXCEPTION_PREFIX + " data=" + e.getData(), e);
            } else {
                log.error(EXCEPTION_PREFIX, e);
            }
        }
        return Result.error(e.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result<Object> otherException(Exception e) {
        log.error(EXCEPTION_PREFIX, e);
        return Result.error(GlobalErrorCode.FAILED.getCode(), "系统异常");
    }
}
