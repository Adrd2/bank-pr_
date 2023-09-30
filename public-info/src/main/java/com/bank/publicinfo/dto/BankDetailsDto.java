package com.bank.publicinfo.dto;

import com.bank.publicinfo.dto.validation.RegexPattern;
import com.bank.publicinfo.entity.BankDetailsEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO для {@link BankDetailsEntity}
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BankDetailsDto implements Serializable {
    @NotNull(message = "Технический идентификатор не должен быть пустым.")
    Long id;

    @Pattern(regexp = "04\\d{7}", message = "БИК должен быть вида 04xxxxxxx")
    String bik;

    @RegexPattern(regexp = "\\d{10}", message = "ИНН должен содержать 10 цифр")
    Long inn;

    @RegexPattern(regexp = "\\d{9}", message = "КПП должен содержать 9 цифр")
    Long kpp;

    @RegexPattern(regexp = "301\\d{17}", message = "Кор.счет должен начинаться с 301 и содержать 20 цифр")
    BigDecimal corAccount;

    @NotEmpty(message = "Название города не должно быть пустым")
    String city;
    @Pattern(regexp = "ПАО$", message = "Банк должен быть ПАО")
    String jointStockCompany;
    @NotEmpty(message = "Название банка не должно быть пустым")
    String name;
}
