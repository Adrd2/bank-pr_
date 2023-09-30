package com.bank.profile.integration.service;

import com.bank.profile.dto.*;
import com.bank.profile.entity.*;
import com.bank.profile.integration.ContainerConfig;
import com.bank.profile.mapper.AccountDetailsIdMapper;
import com.bank.profile.mapper.AccountDetailsIdMapperImpl;
import com.bank.profile.repository.AccountDetailsIdRepository;
import com.bank.profile.service.AccountDetailsIdService;
import com.bank.profile.service.impl.AccountDetailsIdServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityNotFoundException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@Testcontainers
public class AccountDetailsIdServiceTest extends ContainerConfig {
    AccountDetailsIdService service;
    AccountDetailsIdEntity entity;
    AccountDetailsIdDto dto;
    ProfileEntity profile;
    PassportEntity passport;
    ActualRegistrationEntity actualRegistration;
    RegistrationEntity registration;
    AccountDetailsIdRepository repository;
    AccountDetailsIdMapper mapper;
    ProfileDto profileDto;
    ActualRegistrationDto actualRegistrationDto;
    RegistrationDto registrationDto;
    PassportDto passportDto;
    AccountDetailsIdDto accountDetailsIdDto;

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

        repository.save(entity);


        registrationDto = new RegistrationDto(1L, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        passportDto = new PassportDto(1L, 1, 2L, "lastname", "firstname",
                "middlename", "gender", LocalDate.now(), "birthPlace",
                "issuedBy", LocalDate.now(), 123, LocalDate.now(), registrationDto);
        actualRegistrationDto = new ActualRegistrationDto(1L, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        profileDto = new ProfileDto(1L, 123L, "email", "premium_card",
                123L, 123L, passportDto, actualRegistrationDto);


        when(repository.save(entity)).thenReturn(entity);
        when(repository.findById(1L)).thenReturn(java.util.Optional.ofNullable(entity));
    }

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        AccountDetailsIdDto result = service.findById(entity.getId());

        assertAll(
                () -> {
                    assertEquals(entity.getId(), result.getId());
                    assertEquals(entity.getAccountId(), result.getAccountId());
                    assertEquals(entity.getProfile(), result.getProfile());
                }
        );
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    void findByIDNotFoundExceptionNegativeTest() {
        Long id = 123L;
        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
    }

    @Test
    @DisplayName("Поиск по нескольким id, позитивный сценарий")
    void findAllByIdPositiveTest() {
        List<Long> idList = List.of(1L);
        List<AccountDetailsIdDto> result = service.findAllById(idList);

        assertAll(
                () -> {
                    assertEquals(entity.getId(), result.get(0).getId());
                    assertEquals(entity.getAccountId(), result.get(0).getAccountId());
                    assertEquals(entity.getProfile(), result.get(0).getProfile());
                }
        );
    }

    @Test
    @DisplayName("Поиск по нескольким несуществующим id, негативный сценарий")
    void findAllByIdNotFoundExceptionNegativeTest() {
        Long id = 123L;
        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
    }

    @Test
    @DisplayName("Сохранение, позитивный сценарий")
    void savePositiveTest() {
        prepare();
        AccountDetailsIdDto result = service.save(accountDetailsIdDto);

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
        AccountDetailsIdDto actual = service.save(null);
        assertNull(actual);
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updatePositiveTest() {
        prepare();
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
        assertThrows(EntityNotFoundException.class, () -> service.update(id, dto));
    }
}
