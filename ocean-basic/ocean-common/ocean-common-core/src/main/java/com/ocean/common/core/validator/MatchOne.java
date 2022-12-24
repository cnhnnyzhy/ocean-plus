package com.ocean.common.core.validator;


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
 * 包含校验，匹配是否存在
 *
 * @author ocean
 * @since 2022-01-11
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {MatchOne.MatchOneValid.class})
public @interface MatchOne {
    /**
     * 必须的属性
     * 显示 校验信息
     * 利用 {} 获取 属性值，参考了官方的message编写方式
     *
     * @see org.hibernate.validator 静态资源包里面 message 编写方式
     */
    String message() default "{key}值必须包含在 {values} 内";

    /**
     * 必须的属性
     * 用于分组校验
     */
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 非必须
     */
    String[] values() default {};

    /**
     * 非必须
     */
    String key() default "";

    /**
     * 必须实现 ConstraintValidator接口
     */
    class MatchOneValid implements ConstraintValidator<MatchOne, Object> {
        private String[] values;

        @Override
        public void initialize(MatchOne constraintAnnotation) {
            this.values = constraintAnnotation.values();
        }

        /**
         * 校验逻辑的实现
         *
         * @param value 需要校验的 值
         * @return 布尔值结果
         */
        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            if (null == value || "".equals(value.toString())) {
                // 为null 标识不验证
                return true;
            }
            try {
                return Arrays.stream(values).anyMatch(r -> r.equals(value.toString()));
            } catch (Exception e) {
                return false;
            }
        }
    }
}
