package com.ocean.common.core.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Arrays;

/**
 * 检查int值必须在列表中，null不校验
 *
 * @author ocean
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = OneOfInt.OneOfValidator.class)
public @interface OneOfInt {
    String message() default "参数值不合法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int[] value() default {};

    class OneOfValidator implements ConstraintValidator<OneOfInt, Integer> {
        private int[] values;

        @Override
        public void initialize(OneOfInt constraintAnnotation) {
            this.values = constraintAnnotation.value();
        }

        @Override
        public boolean isValid(Integer value, ConstraintValidatorContext context) {
            if (value == null) {
                return true;
            }
            return Arrays.stream(values).filter(v -> v == value).findFirst().isPresent();
        }
    }

}
