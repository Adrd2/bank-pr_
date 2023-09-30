package com.bank.profile.integration.service;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.PassportMapper;
import com.bank.profile.mapper.PassportMapperImpl;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.service.PassportService;
import com.bank.profile.service.impl.PassportServiceImp;
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

public class PassportServiceTest {
    PassportService service;
    PassportEntity entity;
    PassportDto dto;
    PassportRepository repository;
    RegistrationEntity registration;
    PassportMapper mapper;

    @BeforeEach
    void prepare() {
        registration = new RegistrationEntity(1L, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        repository = Mockito.mock(PassportRepository.class);
        mapper = new PassportMapperImpl();

        service = new PassportServiceImp(repository, mapper);
        entity = new PassportEntity(1L, 1, 2L, "lastname", "firstname",
                "middlename", "gender", LocalDate.now(), "birthPlace",
                "issuedBy", LocalDate.now(), 123, LocalDate.now(), registration);
        dto = mapper.toDto(entity);
    }

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        prepare();
        doReturn(Optional.of(entity)).when(repository).findById(entity.getId());
        PassportDto result = service.findById(dto.getId());

        assertAll(
                () -> {
                    assertEquals(dto.getNumber(), result.getNumber());
                    assertEquals(dto.getId(), result.getId());
                    assertEquals(dto.getDivisionCode(), result.getDivisionCode());
                    assertEquals(dto.getSeries(), result.getSeries());
                    assertEquals(dto.getGender(), result.getGender());
                    assertEquals(dto.getBirthDate(), result.getBirthDate());
                    assertEquals(dto.getBirthPlace(), result.getBirthPlace());
                    assertEquals(dto.getDateOfIssue(), result.getDateOfIssue());
                    assertEquals(dto.getExpirationDate(), result.getExpirationDate());
                    assertEquals(dto.getFirstName(), result.getFirstName());
                    assertEquals(dto.getIssuedBy(), result.getIssuedBy());
                    assertEquals(dto.getLastName(), result.getLastName());
                    assertEquals(dto.getMiddleName(), result.getMiddleName());
                    assertEquals(dto.getRegistration(), result.getRegistration());

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
        List<PassportDto> result = service.findAllById(idList);

        assertAll(
                () -> {
                    assertEquals(dto.getGender(), result.get(0).getGender());
                    assertEquals(dto.getId(), result.get(0).getId());
                    assertEquals(dto.getNumber(), result.get(0).getNumber());
                    assertEquals(dto.getSeries(), result.get(0).getSeries());
                    assertEquals(dto.getFirstName(), result.get(0).getFirstName());
                    assertEquals(dto.getIssuedBy(), result.get(0).getIssuedBy());
                    assertEquals(dto.getRegistration(), result.get(0).getRegistration());
                    assertEquals(dto.getMiddleName(), result.get(0).getMiddleName());
                    assertEquals(dto.getLastName(), result.get(0).getLastName());
                    assertEquals(dto.getExpirationDate(), result.get(0).getExpirationDate());
                    assertEquals(dto.getDateOfIssue(), result.get(0).getDateOfIssue());
                    assertEquals(dto.getBirthPlace(), result.get(0).getBirthPlace());
                    assertEquals(dto.getBirthDate(), result.get(0).getBirthDate());
                    assertEquals(dto.getDivisionCode(), result.get(0).getDivisionCode());
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
        PassportDto result = service.save(dto);

        assertAll(
                () -> {
                    assertEquals(dto.getNumber(), result.getNumber());
                    assertEquals(dto.getId(), result.getId());
                    assertEquals(dto.getDivisionCode(), result.getDivisionCode());
                    assertEquals(dto.getSeries(), result.getSeries());
                    assertEquals(dto.getGender(), result.getGender());
                    assertEquals(dto.getBirthDate(), result.getBirthDate());
                    assertEquals(dto.getBirthPlace(), result.getBirthPlace());
                    assertEquals(dto.getDateOfIssue(), result.getDateOfIssue());
                    assertEquals(dto.getExpirationDate(), result.getExpirationDate());
                    assertEquals(dto.getFirstName(), result.getFirstName());
                    assertEquals(dto.getIssuedBy(), result.getIssuedBy());
                    assertEquals(dto.getLastName(), result.getLastName());
                    assertEquals(dto.getMiddleName(), result.getMiddleName());
                    assertEquals(dto.getRegistration(), result.getRegistration());

                }
        );
    }

    @Test
    @DisplayName("Сохранение null, негативный сценарий")
    void saveNullNegativeTest() {
        doReturn(Optional.of(entity)).when(repository).save(isNull());
        PassportDto actual = service.save(dto);
        assertNull(actual);
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updatePositiveTest() {
        doReturn(entity).when(repository).save(any());
        doReturn(Optional.of(entity)).when(repository).findById(entity.getId());

        PassportDto result = service.update(dto.getId(), dto);

        assertAll(
                () -> {
                    assertEquals(dto.getNumber(), result.getNumber());
                    assertEquals(dto.getId(), result.getId());
                    assertEquals(dto.getDivisionCode(), result.getDivisionCode());
                    assertEquals(dto.getSeries(), result.getSeries());
                    assertEquals(dto.getGender(), result.getGender());
                    assertEquals(dto.getBirthDate(), result.getBirthDate());
                    assertEquals(dto.getBirthPlace(), result.getBirthPlace());
                    assertEquals(dto.getDateOfIssue(), result.getDateOfIssue());
                    assertEquals(dto.getExpirationDate(), result.getExpirationDate());
                    assertEquals(dto.getFirstName(), result.getFirstName());
                    assertEquals(dto.getIssuedBy(), result.getIssuedBy());
                    assertEquals(dto.getLastName(), result.getLastName());
                    assertEquals(dto.getMiddleName(), result.getMiddleName());
                    assertEquals(dto.getRegistration(), result.getRegistration());

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

