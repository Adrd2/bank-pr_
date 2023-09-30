package com.bank.account.dto;

import com.bank.account.entity.AccountDetailsEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO сущности  {@link AccountDetailsEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDetailsDto implements Serializable {
    @NotNull(message = "Технический идентификатор не должен быть пустым.")
    Long id;
    @NotNull(message = "Технический идентификатор паспорта не должен быть пустым.")
    Long passportId;
    @NotNull(message = "Номер счета не должен быть пустым.")
    Long accountNumber;
    @NotNull(message = "Технический идентификатор на реквизиты банка не должен быть пустым.")
    Long bankDetailsId;
    @Min(value = 0, message = "Сумма денег на счёте не может быть отрицательной.")
    BigDecimal money;
    @NotNull(message = "Необходимо отметить, есть ли минус на счёте.")
    Boolean negativeBalance;
    @NotNull(message = "Технический идентификатор профиля не должен быть пустым.")
    Long profileId;
}
