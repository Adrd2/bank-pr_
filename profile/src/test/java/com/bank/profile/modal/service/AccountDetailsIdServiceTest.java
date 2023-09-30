package com.bank.profile.modal.service;

import com.bank.profile.dto.AccountDetailsIdDto;

import com.bank.profile.entity.AccountDetailsIdEntity;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.AccountDetailsIdMapper;
import com.bank.profile.mapper.AccountDetailsIdMapperImpl;
import com.bank.profile.repository.AccountDetailsIdRepository;
import com.bank.profile.service.AccountDetailsIdService;
import com.bank.profile.service.impl.AccountDetailsIdServiceImp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
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

public class AccountDetailsIdServiceTest {
    AccountDetailsIdService service;
    AccountDetailsIdEntity entity;
    AccountDetailsIdDto dto;
    ProfileEntity profile;
    PassportEntity passport;
    ActualRegistrationEntity actualRegistration;
    RegistrationEntity registration;
    AccountDetailsIdRepository repository;
    AccountDetailsIdMapper mapper;

    @BeforeEach
    void prepare() {
        registration = new RegistrationEntity(1L, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        passport = new PassportEntity(1L, 1, 2L, "lastname", "firstname",
                "middlename", "gender", LocalDate.now(), "birthPlace",
                "issuedBy", LocalDate.now(), 123, LocalDate.now(), registration);
        actualRegistration = new ActualRegistrationEntity(1L, "country", "region",
                "city", "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        profile = new ProfileEntity(1L, 123L, "email", "premium_card",
                123L, 123L, passport, actualRegistration);


        repository = Mockito.mock(AccountDetailsIdRepository.class);
        mapper = new AccountDetailsIdMapperImpl();

        service = new AccountDetailsIdServiceImp(repository, mapper);
        entity = new AccountDetailsIdEntity(1L, 1L, profile);
        dto = mapper.toDto(entity);
    }

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        doReturn(Optional.of(entity)).when(repository).findById(entity.getId());
        AccountDetailsIdDto result = service.findById(entity.getId());

        assertAll(
                () -> {
                    assertEquals(dto.getId(), result.getId());
                    assertEquals(dto.getAccountId(), result.getAccountId());
                    assertEquals(dto.getProfile(), result.getProfile());
                }
        );
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    void findByIDNotFoundExceptionNegativeTest() {
        Long id = 123L;
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
    }

    @Test
    @DisplayName("Поиск по нескольким id, позитивный сценарий")
    void findAllByIdPositiveTest() {
        List<Long> idList = List.of(1L);
        doReturn(List.of(entity)).when(repository).findAllById(idList);
        List<AccountDetailsIdDto> result = service.findAllById(idList);

        assertAll(
                () -> {
                    assertEquals(dto.getId(), result.get(0).getId());
                    assertEquals(dto.getAccountId(), result.get(0).getAccountId());
                    assertEquals(dto.getProfile(), result.get(0).getProfile());
                }
        );
    }

    @Test
    @DisplayName("Поиск по нескольким несуществующим id, негативный сценарий")
    void findAllByIdNotFoundExceptionNegativeTest() {
        Long id = 123L;
        when(repository.findById(id)).thenReturn(Optional.empty());
        when(repository.findAllById(List.of(123L))).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
    }

    @Test
    @DisplayName("Сохранение, позитивный сценарий")
    void savePositiveTest() {
        doReturn(entity).when(repository).save(any());
        AccountDetailsIdDto result = service.save(dto);

        assertAll(
                () -> {
                    assertEquals(dto.getId(), result.getId());
                    assertEquals(dto.getAccountId(), result.getAccountId());
                    assertEquals(dto.getProfile(), result.getProfile());
                }
        );
    }

    @Test
    @DisplayName("Сохранение null, негативный сценарий")
    void saveNullNegativeTest() {
        doReturn(Optional.of(entity)).when(repository).save(isNull());
        AccountDetailsIdDto actual = service.save(dto);
        assertNull(actual);
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updatePositiveTest() {
        doReturn(entity).when(repository).save(any());
        doReturn(Optional.of(entity)).when(repository).findById(entity.getId());

        AccountDetailsIdDto result = service.update(dto.getId(), dto);

        assertAll(
                () -> {
                    assertEquals(dto.getId(), result.getId());
                    assertEquals(dto.getAccountId(), result.getAccountId());
                    assertEquals(dto.getProfile(), result.getProfile());
                }
        );
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    void updateNotFoundExceptionNegativeTest() {
        prepare();
        Long id = 123L;
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.update(id, dto));
    }
}
