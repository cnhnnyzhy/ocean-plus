package com.ocean.common.core.validator;


import org.apache.commons.lang3.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Arrays;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * string校验，取值是否在允许的枚举范围内
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {OneOfStr.OneOfStrValid.class})
public @interface OneOfStr {
    /**
     * 必须的属性
     * 显示 校验信息
     * 利用 {} 获取 属性值，参考了官方的message编写方式
     *
     * @see org.hibernate.validator 静态资源包里面 message 编写方式
     */
    String message() default "";

    /**
     * 必须的属性
     * 用于分组校验
     */
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 非必须
     */
    String[] values();


    /**
     * 必须实现 ConstraintValidator接口
     */
    class OneOfStrValid implements ConstraintValidator<OneOfStr, String> {
        private String[] values;

        @Override
        public void initialize(OneOfStr constraintAnnotation) {
            this.values = constraintAnnotation.values();
        }

        /**
         * 校验逻辑的实现
         *
         * @param value 需要校验的 值
         * @return 布尔值结果
         */
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (StringUtils.isEmpty(value)) {
                // 为null 标识不验证
                return true;
            }
            try {
                return Arrays.asList(values).contains(value);
            } catch (Exception e) {
                return false;
            }
        }
    }
}
