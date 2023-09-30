package com.bank.account.integration.mapper;

import com.bank.account.AccountApplication;
import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.mapper.AccountDetailsMapper;
import com.bank.account.integration.TestContainerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes =
        {AccountApplication.class})
@Testcontainers
class AccountDetailsMapperIntegrationTest extends TestContainerConfig {

    @Autowired
    AccountDetailsMapper mapper;
    AccountDetailsEntity entity;
    AccountDetailsDto dto;

    @BeforeEach
    void prepare() {
        entity = new AccountDetailsEntity
                (null, 85L, 10L, 2L, BigDecimal.ONE,
                        false, 1L);
        dto = new AccountDetailsDto
                (null, 85L, 10L, 2L, BigDecimal.ONE,
                        false, 1L);
    }

    @Test
    @DisplayName("маппинг в Entity")
    void toEntityTest() {
        AccountDetailsEntity result = mapper.toEntity(dto);

        assertAll(
                () -> {
                    assertNull(result.getId());
                    assertEquals(dto.getPassportId(), result.getPassportId());
                    assertEquals(dto.getAccountNumber(), result.getAccountNumber());
                    assertEquals(dto.getBankDetailsId(), result.getBankDetailsId());
                    assertEquals(dto.getMoney(), result.getMoney());
                    assertEquals(dto.getNegativeBalance(), result.getNegativeBalance());
                    assertEquals(dto.getProfileId(), result.getProfileId());

                }
        );
    }

    @Test
    @DisplayName("маппинг в Entity, на вход подан null")
    void toEntityNullTest() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("маппинг в dto")
    void toDtoTest() {
        AccountDetailsDto actual = mapper.toDto(entity);

        assertAll(
                () -> {
                    assertEquals(entity.getId(), actual.getId());
                    assertEquals(entity.getPassportId(), actual.getPassportId());
                    assertEquals(entity.getAccountNumber(), actual.getAccountNumber());
                    assertEquals(entity.getBankDetailsId(), actual.getBankDetailsId());
                    assertEquals(entity.getMoney(), actual.getMoney());
                    assertEquals(entity.getNegativeBalance(), actual.getNegativeBalance());
                    assertEquals(entity.getProfileId(), actual.getProfileId());

                }
        );
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан null")
    void toDtoNullTest() {
        assertNull(mapper.toDto(null));
    }

    @Test
    @DisplayName("маппинг в список dto, позитивный сценарий")
    void toDtoListPositiveTest() {
        List<AccountDetailsEntity> entityList = new ArrayList<>();
        AccountDetailsEntity entity1 = new AccountDetailsEntity(1L, 123L, 456L,
                789L, BigDecimal.valueOf(1000), false, 1L);
        AccountDetailsEntity entity2 = new AccountDetailsEntity(2L, 234L, 567L,
                890L, BigDecimal.valueOf(2000), true, 2L);
        entityList.add(entity1);
        entityList.add(entity2);
        AccountDetailsDto dto1 = new AccountDetailsDto(1L, 123L, 456L,
                789L, BigDecimal.valueOf(1000), false, 1L);
        AccountDetailsDto dto2 = new AccountDetailsDto(2L, 234L, 567L,
                890L, BigDecimal.valueOf(2000), true, 2L);
        List<AccountDetailsDto> expectedDtoList = new ArrayList<>();
        expectedDtoList.add(dto1);
        expectedDtoList.add(dto2);

        List<AccountDetailsDto> actualDtoList = mapper.toDtoList(entityList);

        assertAll(
                () -> assertNotNull(actualDtoList),
                () -> assertEquals(expectedDtoList.size(), actualDtoList.size()),
                () -> assertEquals(expectedDtoList.get(0), actualDtoList.get(0)),
                () -> assertEquals(expectedDtoList.get(1), actualDtoList.get(1))
        );
    }

    @Test
    @DisplayName("маппинг в список dto, на вход подан null")
    void toDtoListNullTest() {
        List<AccountDetailsDto> actualDtoList = mapper.toDtoList(null);
        assertNull(actualDtoList);
    }

    @Test
    @DisplayName("слияние в Entity")
    void mergeToEntityTest() {
        AccountDetailsEntity actual = mapper.mergeToEntity(entity, dto);

        assertAll(
                () -> {
                    assertEquals(dto.getId(), actual.getId());
                    assertEquals(dto.getPassportId(), actual.getPassportId());
                    assertEquals(dto.getAccountNumber(), actual.getAccountNumber());
                    assertEquals(dto.getBankDetailsId(), actual.getBankDetailsId());
                    assertEquals(dto.getMoney(), actual.getMoney());
                    assertEquals(dto.getNegativeBalance(), actual.getNegativeBalance());
                    assertEquals(dto.getProfileId(), actual.getProfileId());
                }
        );
    }

    @Test
    @DisplayName("слияние в Entity, на вход поданы null")
    void mergeToEntityNullTest() {
        assertNull(mapper.mergeToEntity(null, null));
    }
}