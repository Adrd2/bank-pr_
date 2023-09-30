package com.bank.publicinfo.dto.validation;

import com.bank.publicinfo.dto.validation.impl.RegexPatternNotNullValidatorForBigDecimal;
import com.bank.publicinfo.dto.validation.impl.RegexPatternNotNullValidatorForLong;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {RegexPatternNotNullValidatorForLong.class, RegexPatternNotNullValidatorForBigDecimal.class})
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RegexPattern {

    String message() default "Invalid value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String regexp();
}
