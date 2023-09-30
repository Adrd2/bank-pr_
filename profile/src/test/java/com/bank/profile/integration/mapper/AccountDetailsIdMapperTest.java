package com.bank.profile.integration.mapper;

import com.bank.profile.dto.*;
import com.bank.profile.entity.*;
import com.bank.profile.mapper.AccountDetailsIdMapper;
import com.bank.profile.mapper.AccountDetailsIdMapperImpl;
import com.bank.profile.repository.AccountDetailsIdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class AccountDetailsIdMapperTest {
    AccountDetailsIdEntity entity;
    AccountDetailsIdDto dto;
    AccountDetailsIdRepository repository;
    AccountDetailsIdMapper mapper;
    ProfileEntity profile;
    RegistrationEntity registration;
    PassportEntity passport;
    ActualRegistrationEntity actualRegistration;
    ProfileDto profileDto;
    PassportDto passportDto;
    ActualRegistrationDto actualRegistrationDto;
    RegistrationDto registrationDto;
    AccountDetailsIdMapper mapper2;
    AccountDetailsIdEntity anotherEntity;

    @BeforeEach
    void prepare() {
        registration = new RegistrationEntity(null, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        passport = new PassportEntity(null, 1, 2L, "lastname", "firstname",
                "middlename", "gender", LocalDate.now(), "birthPlace",
                "issuedBy", LocalDate.now(), 123, LocalDate.now(), registration);
        actualRegistration = new ActualRegistrationEntity(null, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        profile = new ProfileEntity(null, 123L, "email", "premium_card",
                123L, 123L, passport, actualRegistration);
        registrationDto = new RegistrationDto(null, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        passportDto = new PassportDto(null, 1, 2L, "lastname", "firstname",
                "middlename", "gender", LocalDate.now(), "birthPlace",
                "issuedBy", LocalDate.now(), 123, LocalDate.now(), registrationDto);

        profileDto = new ProfileDto(null, 123L, "email", "premium_card",
                123L, 123L, passportDto, actualRegistrationDto);
        repository = Mockito.mock(AccountDetailsIdRepository.class);
        mapper = new AccountDetailsIdMapperImpl();

        entity = new AccountDetailsIdEntity(null, 1L, profile);
        dto = new AccountDetailsIdDto(null, 1L, profileDto);
        anotherEntity = new AccountDetailsIdEntity(null, 1L, profile);
        mapper2 = mock(AccountDetailsIdMapper.class);
    }

    @Test
    @DisplayName("Маппинг в энтити")
    void toEntityTest() {
        prepare();
        AccountDetailsIdEntity result = mapper.toEntity(dto);

        assertAll(
                () -> {
                    assertEquals(dto.getId(), result.getId());
                    assertEquals(dto.getAccountId(), result.getAccountId());
                    assertEquals(dto.getProfile().getInn(), result.getProfile().getInn());
                    assertEquals(dto.getProfile().getSnils(), result.getProfile().getSnils());
                    assertEquals(dto.getProfile().getId(), result.getProfile().getId());
                    assertEquals(dto.getProfile().getEmail(), result.getProfile().getEmail());
                    assertEquals(dto.getProfile().getPhoneNumber(), result.getProfile().getPhoneNumber());
                    assertEquals(dto.getProfile().getActualRegistration(),
                            result.getProfile().getActualRegistration());
                    assertEquals(dto.getProfile().getNameOnCard(), result.getProfile().getNameOnCard());
                    assertEquals(dto.getProfile().getPassport().getBirthDate(),
                            result.getProfile().getPassport().getBirthDate());
                    assertEquals(dto.getProfile().getPassport().getDivisionCode(),
                            result.getProfile().getPassport().getDivisionCode());
                    assertEquals(dto.getProfile().getPassport().getNumber(),
                            result.getProfile().getPassport().getNumber());
                    assertEquals(dto.getProfile().getPassport().getGender(),
                            result.getProfile().getPassport().getGender());
                    assertEquals(dto.getProfile().getPassport().getSeries(),
                            result.getProfile().getPassport().getSeries());
                    assertEquals(dto.getProfile().getPassport().getBirthPlace(),
                            result.getProfile().getPassport().getBirthPlace());
                    assertEquals(dto.getProfile().getPassport().getDateOfIssue(),
                            result.getProfile().getPassport().getDateOfIssue());
                    assertEquals(dto.getProfile().getPassport().getExpirationDate(),
                            result.getProfile().getPassport().getExpirationDate());
                    assertEquals(dto.getProfile().getPassport().getFirstName(),
                            result.getProfile().getPassport().getFirstName());
                    assertEquals(dto.getProfile().getPassport().getIssuedBy(),
                            result.getProfile().getPassport().getIssuedBy());
                    assertEquals(dto.getProfile().getPassport().getLastName(),
                            result.getProfile().getPassport().getLastName());
                    assertEquals(dto.getProfile().getPassport().getMiddleName(),
                            result.getProfile().getPassport().getMiddleName());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getId(),
                            result.getProfile().getPassport().getRegistration().getId());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getCity(),
                            result.getProfile().getPassport().getRegistration().getCity());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getCountry(),
                            result.getProfile().getPassport().getRegistration().getCountry());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getIndex(),
                            result.getProfile().getPassport().getRegistration().getIndex());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getDistrict(),
                            result.getProfile().getPassport().getRegistration().getDistrict());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getRegion(),
                            result.getProfile().getPassport().getRegistration().getRegion());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getStreet(),
                            result.getProfile().getPassport().getRegistration().getStreet());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getLocality(),
                            result.getProfile().getPassport().getRegistration().getLocality());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getFlatNumber(),
                            result.getProfile().getPassport().getRegistration().getFlatNumber());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getHouseBlock(),
                            result.getProfile().getPassport().getRegistration().getHouseBlock());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getHouseNumber(),
                            result.getProfile().getPassport().getRegistration().getHouseNumber());
                }
        );
    }

    @Test
    @DisplayName("Маппинг в энтити, на вход подается null")
    void toEntityNullTest() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("Маппинг в дто")
    void toDtoTest() {
        prepare();
        AccountDetailsIdDto result = mapper.toDto(entity);

        assertAll(
                () -> {
                    assertEquals(dto.getId(), result.getId());
                    assertEquals(dto.getAccountId(), result.getAccountId());
                    assertEquals(dto.getProfile().getInn(), result.getProfile().getInn());
                    assertEquals(dto.getProfile().getSnils(), result.getProfile().getSnils());
                    assertEquals(dto.getProfile().getId(), result.getProfile().getId());
                    assertEquals(dto.getProfile().getEmail(), result.getProfile().getEmail());
                    assertEquals(dto.getProfile().getPhoneNumber(), result.getProfile().getPhoneNumber());
                    assertEquals(dto.getProfile().getNameOnCard(), result.getProfile().getNameOnCard());
                    assertEquals(dto.getProfile().getPassport().getBirthDate(),
                            result.getProfile().getPassport().getBirthDate());
                    assertEquals(dto.getProfile().getPassport().getDivisionCode(),
                            result.getProfile().getPassport().getDivisionCode());
                    assertEquals(dto.getProfile().getPassport().getNumber(),
                            result.getProfile().getPassport().getNumber());
                    assertEquals(dto.getProfile().getPassport().getGender(),
                            result.getProfile().getPassport().getGender());
                    assertEquals(dto.getProfile().getPassport().getSeries(),
                            result.getProfile().getPassport().getSeries());
                    assertEquals(dto.getProfile().getPassport().getBirthPlace(),
                            result.getProfile().getPassport().getBirthPlace());
                    assertEquals(dto.getProfile().getPassport().getDateOfIssue(),
                            result.getProfile().getPassport().getDateOfIssue());
                    assertEquals(dto.getProfile().getPassport().getExpirationDate(),
                            result.getProfile().getPassport().getExpirationDate());
                    assertEquals(dto.getProfile().getPassport().getFirstName(),
                            result.getProfile().getPassport().getFirstName());
                    assertEquals(dto.getProfile().getPassport().getIssuedBy(),
                            result.getProfile().getPassport().getIssuedBy());
                    assertEquals(dto.getProfile().getPassport().getLastName(),
                            result.getProfile().getPassport().getLastName());
                    assertEquals(dto.getProfile().getPassport().getMiddleName(),
                            result.getProfile().getPassport().getMiddleName());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getId(),
                            result.getProfile().getPassport().getRegistration().getId());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getCity(),
                            result.getProfile().getPassport().getRegistration().getCity());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getCountry(),
                            result.getProfile().getPassport().getRegistration().getCountry());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getIndex(),
                            result.getProfile().getPassport().getRegistration().getIndex());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getDistrict(),
                            result.getProfile().getPassport().getRegistration().getDistrict());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getRegion(),
                            result.getProfile().getPassport().getRegistration().getRegion());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getStreet(),
                            result.getProfile().getPassport().getRegistration().getStreet());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getLocality(),
                            result.getProfile().getPassport().getRegistration().getLocality());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getFlatNumber(),
                            result.getProfile().getPassport().getRegistration().getFlatNumber());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getHouseBlock(),
                            result.getProfile().getPassport().getRegistration().getHouseBlock());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getHouseNumber(),
                            result.getProfile().getPassport().getRegistration().getHouseNumber());
                }
        );
    }

    @Test
    @DisplayName("Маппинг в dto, на вход подается null")
    void toDtoNullTest() {
        assertNull(mapper.toDto(null));
    }

    @Test
    @DisplayName("Слияние в entity")
    void mergeToEntityTest() {
        prepare();
        AccountDetailsIdEntity result = mapper.mergeToEntity(dto, entity);

        assertAll(
                () -> {
                    assertEquals(dto.getId(), result.getId());
                    assertEquals(dto.getAccountId(), result.getAccountId());
                    assertEquals(dto.getProfile().getInn(), result.getProfile().getInn());
                    assertEquals(dto.getProfile().getSnils(), result.getProfile().getSnils());
                    assertEquals(dto.getProfile().getId(), result.getProfile().getId());
                    assertEquals(dto.getProfile().getEmail(), result.getProfile().getEmail());
                    assertEquals(dto.getProfile().getPhoneNumber(), result.getProfile().getPhoneNumber());
                    assertEquals(dto.getProfile().getNameOnCard(), result.getProfile().getNameOnCard());
                    assertEquals(dto.getProfile().getPassport().getBirthDate(),
                            result.getProfile().getPassport().getBirthDate());
                    assertEquals(dto.getProfile().getPassport().getDivisionCode(),
                            result.getProfile().getPassport().getDivisionCode());
                    assertEquals(dto.getProfile().getPassport().getNumber(),
                            result.getProfile().getPassport().getNumber());
                    assertEquals(dto.getProfile().getPassport().getGender(),
                            result.getProfile().getPassport().getGender());
                    assertEquals(dto.getProfile().getPassport().getSeries(),
                            result.getProfile().getPassport().getSeries());
                    assertEquals(dto.getProfile().getPassport().getBirthPlace(),
                            result.getProfile().getPassport().getBirthPlace());
                    assertEquals(dto.getProfile().getPassport().getDateOfIssue(),
                            result.getProfile().getPassport().getDateOfIssue());
                    assertEquals(dto.getProfile().getPassport().getExpirationDate(),
                            result.getProfile().getPassport().getExpirationDate());
                    assertEquals(dto.getProfile().getPassport().getFirstName(),
                            result.getProfile().getPassport().getFirstName());
                    assertEquals(dto.getProfile().getPassport().getIssuedBy(),
                            result.getProfile().getPassport().getIssuedBy());
                    assertEquals(dto.getProfile().getPassport().getLastName(),
                            result.getProfile().getPassport().getLastName());
                    assertEquals(dto.getProfile().getPassport().getMiddleName(),
                            result.getProfile().getPassport().getMiddleName());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getId(),
                            result.getProfile().getPassport().getRegistration().getId());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getCity(),
                            result.getProfile().getPassport().getRegistration().getCity());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getCountry(),
                            result.getProfile().getPassport().getRegistration().getCountry());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getIndex(),
                            result.getProfile().getPassport().getRegistration().getIndex());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getDistrict(),
                            result.getProfile().getPassport().getRegistration().getDistrict());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getRegion(),
                            result.getProfile().getPassport().getRegistration().getRegion());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getStreet(),
                            result.getProfile().getPassport().getRegistration().getStreet());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getLocality(),
                            result.getProfile().getPassport().getRegistration().getLocality());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getFlatNumber(),
                            result.getProfile().getPassport().getRegistration().getFlatNumber());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getHouseBlock(),
                            result.getProfile().getPassport().getRegistration().getHouseBlock());
                    assertEquals(dto.getProfile().getPassport().getRegistration().getHouseNumber(),
                            result.getProfile().getPassport().getRegistration().getHouseNumber());
                }
        );
    }

    @Test
    @DisplayName("Слияние в entity, на вход подается null")
    void mergeToEntityNullTest() {
        assertNull(mapper.mergeToEntity(null, null));
    }

    @Test
    @DisplayName("Маппинг в список dto")
    void listToDtoTest() {
        prepare();
        doReturn(List.of(anotherEntity)).when(mapper2).toDtoList(List.of(anotherEntity));
        List<AccountDetailsIdDto> result = mapper.toDtoList(List.of(anotherEntity));

        assertAll(
                () -> {
                    assertEquals(result.get(0).getAccountId(), anotherEntity.getAccountId());
                    assertEquals(result.get(0).getId(), anotherEntity.getId());
                }
        );
    }

    @Test
    @DisplayName("Маппинг в список dto, негативный сценарий")
    void listToDtoNegativeTest() {
        prepare();
        doReturn(List.of(anotherEntity)).when(mapper2).toDtoList(List.of(entity));
        List<AccountDetailsIdDto> result = mapper.toDtoList(List.of(entity));
        assertAll(
                () -> {
                    assertEquals(result.get(0).getAccountId(), anotherEntity.getAccountId());
                    assertEquals(result.get(0).getId(), anotherEntity.getId());
                }
        );
    }
}
