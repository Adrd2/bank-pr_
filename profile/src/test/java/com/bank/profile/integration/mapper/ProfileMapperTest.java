package com.bank.profile.integration.mapper;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.ProfileMapper;
import com.bank.profile.mapper.ProfileMapperImpl;
import com.bank.profile.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ProfileMapperTest {
    ProfileEntity entity;
    ProfileDto dto;
    ProfileRepository repository;
    ProfileMapper mapper;
    PassportEntity passport;
    ActualRegistrationEntity actualRegistration;
    RegistrationDto registrationDto;
    PassportDto passportDto;
    RegistrationEntity registration;
    ActualRegistrationDto actualRegistrationDto;
    ProfileMapper mapper2;
    ProfileEntity anotherEntity;

    @BeforeEach
    void prepare() {
        registration = new RegistrationEntity(null, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        registrationDto = new RegistrationDto(null, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        passport = new PassportEntity(null, 1, 2L, "lastname", "firstname",
                "middlename", "gender", LocalDate.now(), "birthPlace",
                "issuedBy", LocalDate.now(), 123, LocalDate.now(), registration);
        actualRegistration = new ActualRegistrationEntity(null, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        passportDto = new PassportDto(null, 1, 2L, "lastname", "firstname",
                "middlename", "gender", LocalDate.now(), "birthPlace",
                "issuedBy", LocalDate.now(), 123, LocalDate.now(), registrationDto);
        actualRegistrationDto = new ActualRegistrationDto(null, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);

        repository = Mockito.mock(ProfileRepository.class);
        mapper = new ProfileMapperImpl();

        entity = new ProfileEntity(null, 1L, "email", "nameOnCard",
                1L, 1L, passport, actualRegistration);
        dto = new ProfileDto(null, null, "email", "nameOnCard",
                1L, 1L, passportDto, actualRegistrationDto);


        anotherEntity = new ProfileEntity(2L, 2L, "Another_email", "Another_nameOnCard",
                2L, 2L, passport, actualRegistration);
        mapper2 = mock(ProfileMapper.class);
    }

    @Test
    @DisplayName("Маппинг в entity")
    void toEntityTest() {
        prepare();
        ProfileEntity result = mapper.toEntity(dto);

        assertAll(
                () -> {
                    assertEquals(result.getId(), dto.getId());
                    assertEquals(result.getSnils(), dto.getSnils());
                    assertEquals(result.getInn(), dto.getInn());
                    assertEquals(result.getEmail(), dto.getEmail());
                    assertEquals(result.getNameOnCard(), dto.getNameOnCard());

                    assertEquals(result.getActualRegistration().getIndex(),
                            dto.getActualRegistration().getIndex());
                    assertEquals(result.getActualRegistration().getStreet(),
                            dto.getActualRegistration().getStreet());
                    assertEquals(result.getActualRegistration().getRegion(),
                            dto.getActualRegistration().getRegion());
                    assertEquals(result.getActualRegistration().getCity(),
                            dto.getActualRegistration().getCity());
                    assertEquals(result.getActualRegistration().getHouseNumber(),
                            dto.getActualRegistration().getHouseNumber());
                    assertEquals(result.getActualRegistration().getHouseBlock(),
                            dto.getActualRegistration().getHouseBlock());
                    assertEquals(result.getActualRegistration().getFlatNumber(),
                            dto.getActualRegistration().getFlatNumber());
                    assertEquals(result.getActualRegistration().getFlatNumber(),
                            dto.getActualRegistration().getFlatNumber());
                    assertEquals(result.getActualRegistration().getLocality(),
                            dto.getActualRegistration().getLocality());
                    assertEquals(result.getActualRegistration().getCountry(),
                            dto.getActualRegistration().getCountry());
                    assertEquals(result.getActualRegistration().getDistrict(),
                            dto.getActualRegistration().getDistrict());

                    assertEquals(result.getPassport().getSeries(), dto.getPassport().getSeries());
                    assertEquals(result.getPassport().getDivisionCode(), dto.getPassport().getDivisionCode());
                    assertEquals(result.getPassport().getNumber(), dto.getPassport().getNumber());
                    assertEquals(result.getPassport().getBirthPlace(), dto.getPassport().getBirthPlace());
                    assertEquals(result.getPassport().getId(), dto.getPassport().getId());
                    assertEquals(result.getPassport().getExpirationDate(), dto.getPassport().getExpirationDate());
                    assertEquals(result.getPassport().getBirthDate(), dto.getPassport().getBirthDate());
                    assertEquals(result.getPassport().getDateOfIssue(), dto.getPassport().getDateOfIssue());
                    assertEquals(result.getPassport().getGender(), dto.getPassport().getGender());
                    assertEquals(result.getPassport().getMiddleName(), dto.getPassport().getMiddleName());
                    assertEquals(result.getPassport().getLastName(), dto.getPassport().getLastName());
                    assertEquals(result.getPassport().getIssuedBy(), dto.getPassport().getIssuedBy());
                    assertEquals(result.getPassport().getFirstName(), dto.getPassport().getFirstName());

                    assertEquals(result.getPassport().getRegistration().getId(),
                            dto.getPassport().getRegistration().getId());
                    assertEquals(result.getPassport().getRegistration().getCity(),
                            dto.getPassport().getRegistration().getCity());
                    assertEquals(result.getPassport().getRegistration().getStreet(),
                            dto.getPassport().getRegistration().getStreet());
                    assertEquals(result.getPassport().getRegistration().getIndex(),
                            dto.getPassport().getRegistration().getIndex());
                    assertEquals(result.getPassport().getRegistration().getFlatNumber(),
                            dto.getPassport().getRegistration().getFlatNumber());
                    assertEquals(result.getPassport().getRegistration().getRegion(),
                            dto.getPassport().getRegistration().getRegion());
                    assertEquals(result.getPassport().getRegistration().getCountry(),
                            dto.getPassport().getRegistration().getCountry());
                    assertEquals(result.getPassport().getRegistration().getDistrict(),
                            dto.getPassport().getRegistration().getDistrict());
                    assertEquals(result.getPassport().getRegistration().getHouseBlock(),
                            dto.getPassport().getRegistration().getHouseBlock());
                    assertEquals(result.getPassport().getRegistration().getHouseNumber(),
                            dto.getPassport().getRegistration().getHouseNumber());
                    assertEquals(result.getPassport().getRegistration().getLocality(),
                            dto.getPassport().getRegistration().getLocality());

                }
        );
    }

    @Test
    @DisplayName("Маппинг в entity, на вход подается null")
    void toEntityNullTest() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("Маппинг в dto")
    void toDtoTest() {
        prepare();
        ProfileDto result = mapper.toDto(entity);

        assertAll(
                () -> {
                    assertEquals(result.getId(), dto.getId());
                    assertEquals(result.getSnils(), dto.getSnils());
                    assertEquals(result.getInn(), dto.getInn());
                    assertEquals(result.getEmail(), dto.getEmail());
                    assertEquals(result.getNameOnCard(), dto.getNameOnCard());

                    assertEquals(result.getActualRegistration().getIndex(),
                            dto.getActualRegistration().getIndex());
                    assertEquals(result.getActualRegistration().getStreet(),
                            dto.getActualRegistration().getStreet());
                    assertEquals(result.getActualRegistration().getRegion(),
                            dto.getActualRegistration().getRegion());
                    assertEquals(result.getActualRegistration().getCity(),
                            dto.getActualRegistration().getCity());
                    assertEquals(result.getActualRegistration().getHouseNumber(),
                            dto.getActualRegistration().getHouseNumber());
                    assertEquals(result.getActualRegistration().getHouseBlock(),
                            dto.getActualRegistration().getHouseBlock());
                    assertEquals(result.getActualRegistration().getFlatNumber(),
                            dto.getActualRegistration().getFlatNumber());
                    assertEquals(result.getActualRegistration().getFlatNumber(),
                            dto.getActualRegistration().getFlatNumber());
                    assertEquals(result.getActualRegistration().getLocality(),
                            dto.getActualRegistration().getLocality());
                    assertEquals(result.getActualRegistration().getCountry(),
                            dto.getActualRegistration().getCountry());
                    assertEquals(result.getActualRegistration().getDistrict(),
                            dto.getActualRegistration().getDistrict());

                    assertEquals(result.getPassport().getSeries(), dto.getPassport().getSeries());
                    assertEquals(result.getPassport().getDivisionCode(), dto.getPassport().getDivisionCode());
                    assertEquals(result.getPassport().getNumber(), dto.getPassport().getNumber());
                    assertEquals(result.getPassport().getBirthPlace(), dto.getPassport().getBirthPlace());
                    assertEquals(result.getPassport().getId(), dto.getPassport().getId());
                    assertEquals(result.getPassport().getExpirationDate(), dto.getPassport().getExpirationDate());
                    assertEquals(result.getPassport().getBirthDate(), dto.getPassport().getBirthDate());
                    assertEquals(result.getPassport().getDateOfIssue(), dto.getPassport().getDateOfIssue());
                    assertEquals(result.getPassport().getGender(), dto.getPassport().getGender());
                    assertEquals(result.getPassport().getMiddleName(), dto.getPassport().getMiddleName());
                    assertEquals(result.getPassport().getLastName(), dto.getPassport().getLastName());
                    assertEquals(result.getPassport().getIssuedBy(), dto.getPassport().getIssuedBy());
                    assertEquals(result.getPassport().getFirstName(), dto.getPassport().getFirstName());

                    assertEquals(result.getPassport().getRegistration().getId(),
                            dto.getPassport().getRegistration().getId());
                    assertEquals(result.getPassport().getRegistration().getCity(),
                            dto.getPassport().getRegistration().getCity());
                    assertEquals(result.getPassport().getRegistration().getStreet(),
                            dto.getPassport().getRegistration().getStreet());
                    assertEquals(result.getPassport().getRegistration().getIndex(),
                            dto.getPassport().getRegistration().getIndex());
                    assertEquals(result.getPassport().getRegistration().getFlatNumber(),
                            dto.getPassport().getRegistration().getFlatNumber());
                    assertEquals(result.getPassport().getRegistration().getRegion(),
                            dto.getPassport().getRegistration().getRegion());
                    assertEquals(result.getPassport().getRegistration().getCountry(),
                            dto.getPassport().getRegistration().getCountry());
                    assertEquals(result.getPassport().getRegistration().getDistrict(),
                            dto.getPassport().getRegistration().getDistrict());
                    assertEquals(result.getPassport().getRegistration().getHouseBlock(),
                            dto.getPassport().getRegistration().getHouseBlock());
                    assertEquals(result.getPassport().getRegistration().getHouseNumber(),
                            dto.getPassport().getRegistration().getHouseNumber());
                    assertEquals(result.getPassport().getRegistration().getLocality(),
                            dto.getPassport().getRegistration().getLocality());
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
        ProfileEntity result = mapper.mergeToEntity(dto, entity);

        assertAll(
                () -> {
                    assertEquals(result.getId(), dto.getId());
                    assertEquals(result.getSnils(), dto.getSnils());
                    assertEquals(result.getInn(), dto.getInn());
                    assertEquals(result.getEmail(), dto.getEmail());
                    assertEquals(result.getNameOnCard(), dto.getNameOnCard());

                    assertEquals(result.getActualRegistration().getIndex(),
                            dto.getActualRegistration().getIndex());
                    assertEquals(result.getActualRegistration().getStreet(),
                            dto.getActualRegistration().getStreet());
                    assertEquals(result.getActualRegistration().getRegion(),
                            dto.getActualRegistration().getRegion());
                    assertEquals(result.getActualRegistration().getCity(),
                            dto.getActualRegistration().getCity());
                    assertEquals(result.getActualRegistration().getHouseNumber(),
                            dto.getActualRegistration().getHouseNumber());
                    assertEquals(result.getActualRegistration().getHouseBlock(),
                            dto.getActualRegistration().getHouseBlock());
                    assertEquals(result.getActualRegistration().getFlatNumber(),
                            dto.getActualRegistration().getFlatNumber());
                    assertEquals(result.getActualRegistration().getFlatNumber(),
                            dto.getActualRegistration().getFlatNumber());
                    assertEquals(result.getActualRegistration().getLocality(),
                            dto.getActualRegistration().getLocality());
                    assertEquals(result.getActualRegistration().getCountry(),
                            dto.getActualRegistration().getCountry());
                    assertEquals(result.getActualRegistration().getDistrict(),
                            dto.getActualRegistration().getDistrict());

                    assertEquals(result.getPassport().getSeries(), dto.getPassport().getSeries());
                    assertEquals(result.getPassport().getDivisionCode(), dto.getPassport().getDivisionCode());
                    assertEquals(result.getPassport().getNumber(), dto.getPassport().getNumber());
                    assertEquals(result.getPassport().getBirthPlace(), dto.getPassport().getBirthPlace());
                    assertEquals(result.getPassport().getId(), dto.getPassport().getId());
                    assertEquals(result.getPassport().getExpirationDate(), dto.getPassport().getExpirationDate());
                    assertEquals(result.getPassport().getBirthDate(), dto.getPassport().getBirthDate());
                    assertEquals(result.getPassport().getDateOfIssue(), dto.getPassport().getDateOfIssue());
                    assertEquals(result.getPassport().getGender(), dto.getPassport().getGender());
                    assertEquals(result.getPassport().getMiddleName(), dto.getPassport().getMiddleName());
                    assertEquals(result.getPassport().getLastName(), dto.getPassport().getLastName());
                    assertEquals(result.getPassport().getIssuedBy(), dto.getPassport().getIssuedBy());
                    assertEquals(result.getPassport().getFirstName(), dto.getPassport().getFirstName());

                    assertEquals(result.getPassport().getRegistration().getId(),
                            dto.getPassport().getRegistration().getId());
                    assertEquals(result.getPassport().getRegistration().getCity(),
                            dto.getPassport().getRegistration().getCity());
                    assertEquals(result.getPassport().getRegistration().getStreet(),
                            dto.getPassport().getRegistration().getStreet());
                    assertEquals(result.getPassport().getRegistration().getIndex(),
                            dto.getPassport().getRegistration().getIndex());
                    assertEquals(result.getPassport().getRegistration().getFlatNumber(),
                            dto.getPassport().getRegistration().getFlatNumber());
                    assertEquals(result.getPassport().getRegistration().getRegion(),
                            dto.getPassport().getRegistration().getRegion());
                    assertEquals(result.getPassport().getRegistration().getCountry(),
                            dto.getPassport().getRegistration().getCountry());
                    assertEquals(result.getPassport().getRegistration().getDistrict(),
                            dto.getPassport().getRegistration().getDistrict());
                    assertEquals(result.getPassport().getRegistration().getHouseBlock(),
                            dto.getPassport().getRegistration().getHouseBlock());
                    assertEquals(result.getPassport().getRegistration().getHouseNumber(),
                            dto.getPassport().getRegistration().getHouseNumber());
                    assertEquals(result.getPassport().getRegistration().getLocality(),
                            dto.getPassport().getRegistration().getLocality());
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
        List<ProfileDto> result = mapper.toDtoList(List.of(anotherEntity));

        assertAll(
                () -> {
                    assertEquals(result.get(0).getId(), anotherEntity.getId());
                    assertEquals(result.get(0).getInn(), anotherEntity.getInn());
                    assertEquals(result.get(0).getPhoneNumber(), anotherEntity.getPhoneNumber());
                    assertEquals(result.get(0).getSnils(), anotherEntity.getSnils());
                    assertEquals(result.get(0).getNameOnCard(), anotherEntity.getNameOnCard());
                    assertEquals(result.get(0).getEmail(), anotherEntity.getEmail());
                }
        );
    }

    @Test
    @DisplayName("Маппинг в список dto, негативный сценарий")
    void listToDtoNegativeTest() {
        prepare();
        doReturn(List.of(anotherEntity)).when(mapper2).toDtoList(List.of(entity));
        List<ProfileDto> result = mapper.toDtoList(List.of(entity));
        assertAll(
                () -> {
                    assertNotEquals(result.get(0).getId(), anotherEntity.getId());
                    assertNotEquals(result.get(0).getInn(), anotherEntity.getInn());
                    assertNotEquals(result.get(0).getPhoneNumber(), anotherEntity.getPhoneNumber());
                    assertNotEquals(result.get(0).getSnils(), anotherEntity.getSnils());
                    assertNotEquals(result.get(0).getNameOnCard(), anotherEntity.getNameOnCard());
                    assertNotEquals(result.get(0).getEmail(), anotherEntity.getEmail());
                }
        );
    }
}

