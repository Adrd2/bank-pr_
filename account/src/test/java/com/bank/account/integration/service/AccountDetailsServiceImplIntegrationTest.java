package com.bank.account.integration.service;

import com.bank.account.AccountApplication;
import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.mapper.AccountDetailsMapper;
import com.bank.account.repository.AccountDetailsRepository;
import com.bank.account.service.AccountDetailsService;
import com.bank.account.service.AccountDetailsServiceImpl;
import com.bank.account.service.common.ExceptionReturner;
import com.bank.account.integration.TestContainerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes =
        {AccountApplication.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Testcontainers
class AccountDetailsServiceImplIntegrationTest extends TestContainerConfig {
    @Autowired
    AccountDetailsRepository repository;
    @Autowired
    AccountDetailsMapper mapper;
    @Autowired
    ExceptionReturner returner;
    AccountDetailsService service;
    AccountDetailsEntity entity;


    @BeforeEach
    void prepare() {
        service = new AccountDetailsServiceImpl(mapper, repository, returner);
        entity = new AccountDetailsEntity
                (1L, 85L, 10L, 2L, BigDecimal.valueOf(100L, 2),
                        false, 1L);
    }

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        repository.save(entity);
        AccountDetailsDto actual = service.findById(entity.getId());

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
    @DisplayName("поиск по несуществующему id, негативный сценарий")
    void findByNonExistIdNegativeTest() {
        Long id = 123L;
        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
    }

    @Test
    @DisplayName("поиск по нескольким id, позитивный сценарий")
    void findAllByIdPositiveTest() {
        repository.save(entity);
        AccountDetailsDto actual = service.findAllById(List.of(1L)).get(0);
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
    @DisplayName("поиск по нескольким несуществующим id, негативный сценарий")
    void findAllByNonExistIdNegativeTest() {
        Long id = 123L;
        assertThrows(EntityNotFoundException.class, () -> service.findAllById(List.of(id)));
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void saveAccountDetailsPositiveTest() {
        AccountDetailsDto dto = new AccountDetailsDto(1L, 85L,
                123456789L, 200L,
                BigDecimal.valueOf(100L, 2),
                false, 1L);
        AccountDetailsDto savedDto = service.save(dto);

        assertAll(
                () -> {
                    assertNotNull(savedDto.getId());
                    assertEquals(dto.getPassportId(), savedDto.getPassportId());
                    assertEquals(dto.getAccountNumber(), savedDto.getAccountNumber());
                    assertEquals(dto.getBankDetailsId(), savedDto.getBankDetailsId());
                    assertEquals(dto.getMoney(), savedDto.getMoney());
                    assertEquals(dto.getNegativeBalance(), savedDto.getNegativeBalance());
                    assertEquals(dto.getProfileId(), savedDto.getProfileId());
                }
        );
    }

    @Test
    @DisplayName("сохранение с null данными, негативный сценарий")
    void saveNullDtoNegativeTest() {
        AccountDetailsDto nullDto = new AccountDetailsDto();
        assertThrows(DataIntegrityViolationException.class, () -> service.save(nullDto));
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() {
        repository.save(entity);
        AccountDetailsDto updatedDto = service.update(entity.getId(), mapper.toDto(entity));

        assertAll(
                () -> {
                    assertEquals(entity.getId(), updatedDto.getId());
                    assertEquals(entity.getPassportId(), updatedDto.getPassportId());
                    assertEquals(entity.getAccountNumber(), updatedDto.getAccountNumber());
                    assertEquals(entity.getBankDetailsId(), updatedDto.getBankDetailsId());
                    assertEquals(entity.getMoney(), updatedDto.getMoney());
                    assertEquals(entity.getNegativeBalance(), updatedDto.getNegativeBalance());
                    assertEquals(entity.getProfileId(), updatedDto.getProfileId());
                }
        );
    }

    @Test
    @DisplayName("обновление по несуществующему id, негативный сценарий")
    void updateNonExistIdNegativeTest() {
        Long id = 123L;
        assertThrows(EntityNotFoundException.class, () -> service.update(id, mapper.toDto(entity)));
    }
}