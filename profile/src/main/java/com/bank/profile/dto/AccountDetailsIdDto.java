package com.bank.profile.dto;

import com.bank.profile.entity.AccountDetailsIdEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * ДТО для сущности {@link AccountDetailsIdEntity}
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailsIdDto implements Serializable {

    private Long id;
    private Long accountId;
    private ProfileDto profile;
}
