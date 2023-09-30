package com.bank.profile.integration.service;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.mapper.ActualRegistrationMapper;
import com.bank.profile.mapper.ActualRegistrationMapperImpl;
import com.bank.profile.repository.ActualRegistrationRepository;
import com.bank.profile.service.ActualRegistrationService;
import com.bank.profile.service.impl.ActualRegistrationServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class ActualRegistrationServiceTest {
    ActualRegistrationService service;
    ActualRegistrationEntity entity;
    ActualRegistrationDto dto;
    ActualRegistrationRepository repository;
    ActualRegistrationMapper mapper;

    @BeforeEach
    void prepare() {
        repository = Mockito.mock(ActualRegistrationRepository.class);
        mapper = new ActualRegistrationMapperImpl();
        service = new ActualRegistrationServiceImp(repository, mapper);
        entity = new ActualRegistrationEntity(1L, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        dto = mapper.toDto(entity);
    }

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        doReturn(Optional.of(entity)).when(repository).findById(entity.getId());
        ActualRegistrationDto result = service.findById(entity.getId());

        assertAll(
                () -> {
                    assertEquals(result.getId(), dto.getId());
                    assertEquals(result.getIndex(), dto.getId());
                    assertEquals(result.getCity(), dto.getCity());
                    assertEquals(result.getCountry(), result.getCountry());
                    assertEquals(result.getRegion(), dto.getRegion());
                    assertEquals(result.getStreet(), dto.getStreet());
                    assertEquals(result.getDistrict(), dto.getDistrict());
                    assertEquals(result.getLocality(), dto.getLocality());
                    assertEquals(result.getHouseNumber(), dto.getHouseNumber());
                    assertEquals(result.getHouseBlock(), dto.getHouseBlock());
                    assertEquals(result.getFlatNumber(), dto.getFlatNumber());
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
    void findAllByIDPositiveTest() {
        List<Long> idList = List.of(1L);
        doReturn(List.of(entity)).when(repository).findAllById(idList);
        List<ActualRegistrationDto> result = service.findAllById(idList);

        assertAll(
                () -> {
                    assertEquals(dto.getId(), result.get(0).getId());
                    assertEquals(dto.getIndex(), result.get(0).getIndex());
                    assertEquals(dto.getCity(), result.get(0).getCity());
                    assertEquals(dto.getCountry(), result.get(0).getCountry());
                    assertEquals(dto.getRegion(), result.get(0).getRegion());
                    assertEquals(dto.getStreet(), result.get(0).getStreet());
                    assertEquals(dto.getDistrict(), result.get(0).getDistrict());
                    assertEquals(dto.getLocality(), result.get(0).getLocality());
                    assertEquals(dto.getHouseBlock(), result.get(0).getHouseBlock());
                    assertEquals(dto.getFlatNumber(), result.get(0).getFlatNumber());
                    assertEquals(dto.getHouseNumber(), result.get(0).getHouseNumber());
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
        ActualRegistrationDto result = service.save(dto);

        assertAll(
                () -> {
                    assertEquals(result.getId(), dto.getId());
                    assertEquals(result.getIndex(), dto.getId());
                    assertEquals(result.getCity(), dto.getCity());
                    assertEquals(result.getCountry(), result.getCountry());
                    assertEquals(result.getRegion(), dto.getRegion());
                    assertEquals(result.getStreet(), dto.getStreet());
                    assertEquals(result.getDistrict(), dto.getDistrict());
                    assertEquals(result.getLocality(), dto.getLocality());
                    assertEquals(result.getHouseNumber(), dto.getHouseNumber());
                    assertEquals(result.getHouseBlock(), dto.getHouseBlock());
                    assertEquals(result.getFlatNumber(), dto.getFlatNumber());
                }
        );
    }

    @Test
    @DisplayName("Сохранение null, негативный сценарий")
    void saveNullNegativeTest() {
        doReturn(Optional.of(entity)).when(repository).save(isNull());
        ActualRegistrationDto actual = service.save(dto);
        assertNull(actual);
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updatePositiveTest() {
        doReturn(entity).when(repository).save(any());
        doReturn(Optional.of(entity)).when(repository).findById(entity.getId());

        ActualRegistrationDto result = service.update(dto.getId(), dto);

        assertAll(
                () -> {
                    assertEquals(result.getId(), dto.getId());
                    assertEquals(result.getIndex(), dto.getId());
                    assertEquals(result.getCity(), dto.getCity());
                    assertEquals(result.getCountry(), result.getCountry());
                    assertEquals(result.getRegion(), dto.getRegion());
                    assertEquals(result.getStreet(), dto.getStreet());
                    assertEquals(result.getDistrict(), dto.getDistrict());
                    assertEquals(result.getLocality(), dto.getLocality());
                    assertEquals(result.getHouseNumber(), dto.getHouseNumber());
                    assertEquals(result.getHouseBlock(), dto.getHouseBlock());
                    assertEquals(result.getFlatNumber(), dto.getFlatNumber());
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
