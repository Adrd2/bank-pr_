package com.bank.antifraud.util.validator;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.common.exception.ValidationException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SuspiciousAccountTransferDtoValidator implements Validator {
    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return SuspiciousAccountTransferDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        final SuspiciousAccountTransferDto dto = (SuspiciousAccountTransferDto) target;

        if (Boolean.TRUE.equals(dto.getIsBlocked())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "blockedReason", "NotEmpty");
        }
    }

    public void validateDto(SuspiciousAccountTransferDto dto) {

        final Errors errors = new BeanPropertyBindingResult(dto, "SuspiciousAccountTransferDto");
        validate(dto, errors);
        if (errors.hasErrors()) {
            throw new ValidationException("Нужно указать причину блокировки");
        }
    }
}
