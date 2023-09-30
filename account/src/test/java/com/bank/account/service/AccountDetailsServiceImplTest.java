package com.bank.account.service;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.mapper.AccountDetailsMapper;
import com.bank.account.mapper.AccountDetailsMapperImpl;
import com.bank.account.repository.AccountDetailsRepository;
import com.bank.account.service.common.ExceptionReturner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(initializers = ConfigDataApplicationContextInitializer.class, classes = {
        AccountDetailsServiceImpl.class, AccountDetailsMapperImpl.class, ExceptionReturner.class
})
class AccountDetailsServiceImplTest {
    @MockBean
    AccountDetailsRepository repository;
    @Autowired
    AccountDetailsService service;
    @Autowired
    AccountDetailsMapper mapper;
    @Autowired
    ExceptionReturner returner;
    AccountDetailsEntity entity;
    AccountDetailsDto dto;

    @BeforeEach
    void prepare() {
        entity = new AccountDetailsEntity
                (1L, 85L, 10L, 2L, BigDecimal.ONE,
                        false, 1L);
        dto = mapper.toDto(entity);
    }

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        doReturn(Optional.of(entity)).when(repository).findById(entity.getId());
        AccountDetailsDto actual = service.findById(entity.getId());

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
    @DisplayName("поиск по несуществующему id, негативный сценарий")
    void findByNonExistIdNegativeTest() {
        Long id = 123L;
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
    }

    @Test
    @DisplayName("поиск по нескольким id, позитивный сценарий")
    void findAllByIdPositiveTest() {
        doReturn(Optional.of(entity)).when(repository).findById(entity.getId());

        AccountDetailsDto actual = service.findAllById(List.of(1L)).get(0);
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
    @DisplayName("поиск по нескольким несуществующим id, негативный сценарий")
    void findAllByNonExistIdNegativeTest() {
        Long id = 123L;
        when(repository.findById(id)).thenReturn(Optional.empty());
        when(repository.findAllById(List.of(123L))).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void savePositiveTest() {
        doReturn(entity).when(repository).save(any());
        AccountDetailsDto actual = service.save(dto);

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
    @DisplayName("сохранение null dto, негативный сценарий")
    void saveNullDtoNegativeTest() {
        doReturn(Optional.of(entity)).when(repository).save(isNull());
        AccountDetailsDto actual = service.save(dto);
        assertNull(actual);
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() {
        doReturn(entity).when(repository).save(any());
        doReturn(Optional.of(entity)).when(repository).findById(entity.getId());
        AccountDetailsDto actual = service.update(dto.getId(), dto);

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
    @DisplayName("обновление по несуществующему id, негативный сценарий")
    void updateNonExistIdNegativeTest() {
        Long id = 123L;
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
    }
}