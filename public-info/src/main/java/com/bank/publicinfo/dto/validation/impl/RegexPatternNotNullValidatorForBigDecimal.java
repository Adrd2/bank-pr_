package com.bank.publicinfo.dto.validation.impl;

import com.bank.publicinfo.dto.validation.RegexPattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class RegexPatternNotNullValidatorForBigDecimal implements ConstraintValidator<RegexPattern, BigDecimal> {
    private String regexPattern;

    @Override
    public void initialize(RegexPattern constraintAnnotation) {
        regexPattern = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }
        final String valueString = String.valueOf(value);
        return valueString.matches(regexPattern);
    }
}
