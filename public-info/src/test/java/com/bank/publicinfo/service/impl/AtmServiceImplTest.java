package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.AtmEntity;
import com.bank.publicinfo.entity.BranchEntity;
import com.bank.publicinfo.mapper.AtmMapperImpl;
import com.bank.publicinfo.repository.AtmRepository;
import com.bank.publicinfo.util.EntityNotFoundSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.persistence.EntityNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;

@SpringJUnitConfig(initializers = ConfigDataApplicationContextInitializer.class, classes = {
        AtmServiceImpl.class, AtmMapperImpl.class, EntityNotFoundSupplier.class})
class AtmServiceImplTest {
    @MockBean
    private AtmRepository repository;
    @Autowired
    private AtmServiceImpl service;

    private AtmDto dto;
    private AtmDto actual;
    private AtmEntity entity;
    private AtmEntity entity2;
    List<AtmEntity> entityList;

    @BeforeEach
    void setUp() {
        entityList = new ArrayList<>();
        dto = new AtmDto(1L, "address", LocalTime.MIN, LocalTime.MAX, true,
                new BranchDto(1L, "address", 89998889988L, "City",
                        LocalTime.MIN, LocalTime.MAX));
        entity = new AtmEntity(1L, "address", LocalTime.MIN, LocalTime.MAX, true,
                new BranchEntity(1L, "address", 89998889988L, "City",
                        LocalTime.MIN, LocalTime.MAX));
        entity2 = new AtmEntity(2L, "address", LocalTime.MIN, LocalTime.MAX, true,
                new BranchEntity(2L, "address", 89998889988L, "City",
                        LocalTime.MIN, LocalTime.MAX));
    }

    @Test
    @DisplayName("поиск по нескольким id, позитивный сценарий")
    void findAllByIdPositiveTest() {
        ArrayList<Long> idList = new ArrayList<>(Arrays.asList(1L, 2L));
        entityList.add(entity);
        entityList.add(entity2);
        Mockito.doReturn(entityList).when(repository).findAllById(idList);
        assertThat(entityList.size()).isEqualTo(service.findAllById(idList).size());
    }

    @Test
    @DisplayName("поиск по нескольким несуществующим id, негативный сценарий")
    void findAllByIdNegativeTest() {
        ArrayList<Long> idList = new ArrayList<>(Arrays.asList(1L, 2L));
        Mockito.doReturn(new ArrayList<>()).when(repository).findAllById(idList);
        assertThrows(EntityNotFoundException.class, () -> service.findAllById(idList));
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void createPositiveTest() {
        Mockito.doReturn(entity).when(repository).save(any());
        actual = service.create(dto);
        assertThat(dto).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    @DisplayName("сохранение null dto, негативный сценарий")
    void createIfNullDtoNegativeTest() {
        Mockito.doReturn(Optional.of(entity)).when(repository).save(isNull());
        actual = service.create(dto);
        assertNull(actual);
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() {
        Mockito.doReturn(entity).when(repository).save(entity);
        Mockito.doReturn(Optional.of(entity)).when(repository).findById(entity.getId());
        actual = service.update(dto.getId(), dto);
        assertThat(dto).usingRecursiveComparison().isEqualTo(actual);

    }

    @Test
    @DisplayName("обновление по несуществующему id, негативный сценарий")
    void updateNegativeTest() {
        Mockito.doReturn(Optional.empty()).when(repository).findById(entity.getId());
        assertThrows(EntityNotFoundException.class, () -> service.update(dto.getId(), dto));
    }

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        Mockito.doReturn(Optional.of(entity)).when(repository).findById(dto.getId());
        actual = service.findById(dto.getId());
        assertThat(dto).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    void findByIdNegativeTest() {
        Mockito.doReturn(Optional.empty()).when(repository).findById(dto.getId());
        assertThrows(EntityNotFoundException.class, () -> service.findById(dto.getId()));
    }
}