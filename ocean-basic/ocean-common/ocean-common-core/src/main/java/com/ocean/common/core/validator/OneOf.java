package com.ocean.common.core.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Arrays;

/**
 * 检查String值必须在列表中，null不校验
 *
 * @author ocean
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = OneOf.OneOfValidator.class)
public @interface OneOf {
    String message() default "参数值不合法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] value() default {};

    class OneOfValidator implements ConstraintValidator<OneOf, String> {
        private String[] values;

        @Override
        public void initialize(OneOf constraintAnnotation) {
            this.values = constraintAnnotation.value();
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null || "".equals(value)) {
                return true;
            }
            return Arrays.stream(values).filter(v -> v.equals(value)).findFirst().isPresent();
        }
    }

}
