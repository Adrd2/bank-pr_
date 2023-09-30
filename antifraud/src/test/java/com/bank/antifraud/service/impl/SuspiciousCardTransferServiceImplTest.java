package com.bank.antifraud.service.impl;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransferEntity;
import com.bank.antifraud.mappers.SuspiciousCardTransferMapper;
import com.bank.antifraud.mappers.SuspiciousCardTransferMapperImpl;
import com.bank.antifraud.repository.SuspiciousCardTransferRepository;
import com.bank.antifraud.service.SuspiciousCardTransferService;
import com.bank.antifraud.service.common.ExceptionReturner;
import com.bank.antifraud.util.validator.SuspiciousCardTransferDtoValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(initializers = ConfigDataApplicationContextInitializer.class, classes = {
        SuspiciousCardTransferServiceImpl.class, SuspiciousCardTransferMapperImpl.class, ExceptionReturner.class
})
class SuspiciousCardTransferServiceImplTest {
    @MockBean
    SuspiciousCardTransferRepository repository;

    @Autowired
    SuspiciousCardTransferMapper mapper;

    @Autowired
    ExceptionReturner exceptionReturner;

    @Autowired
    SuspiciousCardTransferService service;

    @MockBean
    SuspiciousCardTransferDtoValidator dtoValidator;

    private static SuspiciousCardTransferDto dto;
    private static SuspiciousCardTransferEntity entity;
    private static SuspiciousCardTransferEntity entity2;
    private static List<Long> list;

    @BeforeAll
    static void init() {
        dto = new SuspiciousCardTransferDto(null, 1L, false
                , false, "blockedReason1", "suspicionReason1");
        entity = new SuspiciousCardTransferEntity(1L, 1L, false
                , false, "blockedReason1", "suspicionReason1");
        entity2 = new SuspiciousCardTransferEntity(2L, 2L, true
                , true, "blockedReason2", "suspicionReason2");
        list = new ArrayList<>(Arrays.asList(1L, 2L));
    }

    @Test
    @DisplayName("Сохранение, позитивный сценарий")
    void savePositiveTest() {
        Mockito.when(repository.save(mapper.toEntity(dto))).thenReturn(entity);
        SuspiciousCardTransferDto save = service.save(dto);

        assertAll(() -> {
            assertEquals(dto.getBlockedReason(), save.getBlockedReason());
            assertNotEquals(dto.getId(), save.getId());
        });
    }

    @Test
    @DisplayName("Сохранение, негативный сценарий")
    void saveNegativeTest() {
        Mockito.when(repository.save(mapper.toEntity(null))).thenReturn(null);
        SuspiciousCardTransferDto save = service.save(null);
        assertNull(save);
    }

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(entity));
        SuspiciousCardTransferDto byId = service.findById(1L);

        assertAll(() -> {
            assertEquals(dto.getBlockedReason(), byId.getBlockedReason());
            assertNotEquals(dto.getId(), byId.getId());
            assertDoesNotThrow(() -> service.findById(1L));
        });
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    void findByIdNegativeTest() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> service.findById(1L));
        assertEquals("SuspiciousCardTransfer по данному id не существует", entityNotFoundException.getMessage());

    }

    @Test
    @DisplayName("Обновление, позитивный сценарий")
    void updatePositiveTest() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(entity));
        Mockito.when(repository.save(entity)).thenReturn(entity);
        SuspiciousCardTransferDto update = service.update(1L, dto);
        assertEquals(1L, update.getId());
        assertDoesNotThrow(() -> service.update(1L, dto));
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    void updateNegativeTest() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> service.update(1L, dto));
        assertEquals("SuspiciousCardTransfer по данному id не существует", entityNotFoundException.getMessage());
    }

    @Test
    @DisplayName("Поиск всех сущностей, позитивный сценарий")
    void findAllByIdPositiveTest() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(entity));
        Mockito.when(repository.findById(2L)).thenReturn(Optional.of(entity2));
        List<SuspiciousCardTransferDto> allById = service.findAllById(list);
        assertEquals(entity.getBlockedReason(), allById.get(0).getBlockedReason());
        assertEquals(entity2.getBlockedReason(), allById.get(1).getBlockedReason());
        assertDoesNotThrow(() -> service.findAllById(list));
    }

    @Test
    @DisplayName("Поиск всех сущностей, негативный сценарий")
    void findAllByIdNegativeTest() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException entityNotFoundException = assertThrows(EntityNotFoundException.class, () -> service.findAllById(list));
        assertEquals("SuspiciousCardTransfer по данному id не существует", entityNotFoundException.getMessage());
    }
}