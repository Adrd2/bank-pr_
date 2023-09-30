package com.bank.profile.integration.service;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.ProfileMapper;
import com.bank.profile.mapper.ProfileMapperImpl;
import com.bank.profile.repository.ProfileRepository;
import com.bank.profile.service.ProfileService;
import com.bank.profile.service.impl.ProfileServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class ProfileServiceTest {
    ProfileService service;
    ProfileEntity entity;
    ProfileDto dto;
    ProfileMapper mapper;
    ProfileRepository repository;
    RegistrationEntity registration;
    ActualRegistrationEntity actualRegistration;
    PassportEntity passport;

    @BeforeEach
    void prepare() {
        passport = new PassportEntity(1L, 1, 2L, "lastname", "firstname",
                "middlename", "gender", LocalDate.now(), "birthPlace",
                "issuedBy", LocalDate.now(), 123, LocalDate.now(), registration);
        actualRegistration = new ActualRegistrationEntity(1L, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        registration = new RegistrationEntity(1L, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        repository = Mockito.mock(ProfileRepository.class);
        mapper = new ProfileMapperImpl();

        service = new ProfileServiceImp(repository, mapper);
        entity = new ProfileEntity(1L, 1L, "email", "nameOnCard",
                1L, 1L, passport, actualRegistration);
        dto = mapper.toDto(entity);
    }

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        prepare();
        doReturn(Optional.of(entity)).when(repository).findById(entity.getId());
        ProfileDto result = service.findById(dto.getId());

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

                    assertEquals(result.getPassport().getSeries(),
                            dto.getPassport().getSeries());
                    assertEquals(result.getPassport().getDivisionCode(),
                            dto.getPassport().getDivisionCode());
                    assertEquals(result.getPassport().getNumber(),
                            dto.getPassport().getNumber());
                    assertEquals(result.getPassport().getBirthPlace(),
                            dto.getPassport().getBirthPlace());
                    assertEquals(result.getPassport().getId(),
                            dto.getPassport().getId());
                    assertEquals(result.getPassport().getExpirationDate(),
                            dto.getPassport().getExpirationDate());
                    assertEquals(result.getPassport().getBirthDate(),
                            dto.getPassport().getBirthDate());
                    assertEquals(result.getPassport().getDateOfIssue(),
                            dto.getPassport().getDateOfIssue());
                    assertEquals(result.getPassport().getGender()
                            , dto.getPassport().getGender());
                    assertEquals(result.getPassport().getMiddleName(),
                            dto.getPassport().getMiddleName());
                    assertEquals(result.getPassport().getLastName(),
                            dto.getPassport().getLastName());
                    assertEquals(result.getPassport().getIssuedBy(),
                            dto.getPassport().getIssuedBy());
                    assertEquals(result.getPassport().getFirstName(),
                            dto.getPassport().getFirstName());

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
        List<ProfileDto> result = service.findAllById(idList);

        assertAll(
                () -> {
                    assertEquals(result.get(0).getId(), dto.getId());
                    assertEquals(result.get(0).getSnils(), dto.getSnils());
                    assertEquals(result.get(0).getInn(), dto.getInn());
                    assertEquals(result.get(0).getEmail(), dto.getEmail());
                    assertEquals(result.get(0).getNameOnCard(), dto.getNameOnCard());

                    assertEquals(result.get(0).getActualRegistration().getIndex(),
                            dto.getActualRegistration().getIndex());
                    assertEquals(result.get(0).getActualRegistration().getStreet(),
                            dto.getActualRegistration().getStreet());
                    assertEquals(result.get(0).getActualRegistration().getRegion(),
                            dto.getActualRegistration().getRegion());
                    assertEquals(result.get(0).getActualRegistration().getCity(),
                            dto.getActualRegistration().getCity());
                    assertEquals(result.get(0).getActualRegistration().getHouseNumber(),
                            dto.getActualRegistration().getHouseNumber());
                    assertEquals(result.get(0).getActualRegistration().getHouseBlock(),
                            dto.getActualRegistration().getHouseBlock());
                    assertEquals(result.get(0).getActualRegistration().getFlatNumber(),
                            dto.getActualRegistration().getFlatNumber());
                    assertEquals(result.get(0).getActualRegistration().getFlatNumber(),
                            dto.getActualRegistration().getFlatNumber());
                    assertEquals(result.get(0).getActualRegistration().getLocality(),
                            dto.getActualRegistration().getLocality());
                    assertEquals(result.get(0).getActualRegistration().getCountry(),
                            dto.getActualRegistration().getCountry());
                    assertEquals(result.get(0).getActualRegistration().getDistrict(),
                            dto.getActualRegistration().getDistrict());

                    assertEquals(result.get(0).getPassport().getSeries(),
                            dto.getPassport().getSeries());
                    assertEquals(result.get(0).getPassport().getDivisionCode(),
                            dto.getPassport().getDivisionCode());
                    assertEquals(result.get(0).getPassport().getNumber(),
                            dto.getPassport().getNumber());
                    assertEquals(result.get(0).getPassport().getBirthPlace(),
                            dto.getPassport().getBirthPlace());
                    assertEquals(result.get(0).getPassport().getId(),
                            dto.getPassport().getId());
                    assertEquals(result.get(0).getPassport().getExpirationDate(),
                            dto.getPassport().getExpirationDate());
                    assertEquals(result.get(0).getPassport().getBirthDate(),
                            dto.getPassport().getBirthDate());
                    assertEquals(result.get(0).getPassport().getDateOfIssue(),
                            dto.getPassport().getDateOfIssue());
                    assertEquals(result.get(0).getPassport().getGender(),
                            dto.getPassport().getGender());
                    assertEquals(result.get(0).getPassport().getMiddleName(),
                            dto.getPassport().getMiddleName());
                    assertEquals(result.get(0).getPassport().getLastName(),
                            dto.getPassport().getLastName());
                    assertEquals(result.get(0).getPassport().getIssuedBy(),
                            dto.getPassport().getIssuedBy());
                    assertEquals(result.get(0).getPassport().getFirstName(),
                            dto.getPassport().getFirstName());

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
        ProfileDto result = service.save(dto);

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

                    assertEquals(result.getPassport().getSeries(),
                            dto.getPassport().getSeries());
                    assertEquals(result.getPassport().getDivisionCode(),
                            dto.getPassport().getDivisionCode());
                    assertEquals(result.getPassport().getNumber(),
                            dto.getPassport().getNumber());
                    assertEquals(result.getPassport().getBirthPlace(),
                            dto.getPassport().getBirthPlace());
                    assertEquals(result.getPassport().getId(),
                            dto.getPassport().getId());
                    assertEquals(result.getPassport().getExpirationDate(),
                            dto.getPassport().getExpirationDate());
                    assertEquals(result.getPassport().getRegistration(),
                            dto.getPassport().getRegistration());
                    assertEquals(result.getPassport().getBirthDate(),
                            dto.getPassport().getBirthDate());
                    assertEquals(result.getPassport().getDateOfIssue(),
                            dto.getPassport().getDateOfIssue());
                    assertEquals(result.getPassport().getGender(),
                            dto.getPassport().getGender());
                    assertEquals(result.getPassport().getMiddleName(),
                            dto.getPassport().getMiddleName());
                    assertEquals(result.getPassport().getLastName(),
                            dto.getPassport().getLastName());
                    assertEquals(result.getPassport().getIssuedBy(),
                            dto.getPassport().getIssuedBy());
                    assertEquals(result.getPassport().getFirstName(),
                            dto.getPassport().getFirstName());

                }
        );
    }

    @Test
    @DisplayName("Сохранение null, негативный сценарий")
    void saveNullNegativeTest() {
        doReturn(Optional.of(entity)).when(repository).save(isNull());
        ProfileDto actual = service.save(dto);
        assertNull(actual);
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updatePositiveTest() {
        doReturn(entity).when(repository).save(any());
        doReturn(Optional.of(entity)).when(repository).findById(entity.getId());

        ProfileDto result = service.update(dto.getId(), dto);

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

                    assertEquals(result.getPassport().getSeries(),
                            dto.getPassport().getSeries());
                    assertEquals(result.getPassport().getDivisionCode(),
                            dto.getPassport().getDivisionCode());
                    assertEquals(result.getPassport().getNumber(),
                            dto.getPassport().getNumber());
                    assertEquals(result.getPassport().getBirthPlace(),
                            dto.getPassport().getBirthPlace());
                    assertEquals(result.getPassport().getId(),
                            dto.getPassport().getId());
                    assertEquals(result.getPassport().getExpirationDate(),
                            dto.getPassport().getExpirationDate());
                    assertEquals(result.getPassport().getRegistration(),
                            dto.getPassport().getRegistration());
                    assertEquals(result.getPassport().getBirthDate(),
                            dto.getPassport().getBirthDate());
                    assertEquals(result.getPassport().getDateOfIssue(),
                            dto.getPassport().getDateOfIssue());
                    assertEquals(result.getPassport().getGender(),
                            dto.getPassport().getGender());
                    assertEquals(result.getPassport().getMiddleName(),
                            dto.getPassport().getMiddleName());
                    assertEquals(result.getPassport().getLastName(),
                            dto.getPassport().getLastName());
                    assertEquals(result.getPassport().getIssuedBy(),
                            dto.getPassport().getIssuedBy());
                    assertEquals(result.getPassport().getFirstName(),
                            dto.getPassport().getFirstName());

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
