package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.entity.AuditEntity;
import com.bank.publicinfo.mapper.AuditMapperImpl;
import com.bank.publicinfo.repository.AuditRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(initializers = ConfigDataApplicationContextInitializer.class, classes = {
        AuditServiceImpl.class, AuditMapperImpl.class})
class AuditServiceImplTest {
    @MockBean
    AuditRepository repository;
    @Autowired
    AuditServiceImpl service;

    private AuditDto dto;
    private AuditEntity entity;

    @BeforeEach
    void setUp() {
        dto = new AuditDto(1L, "entityType", "operationType", "createdBy",
                "modifiedBy", Timestamp.valueOf("2000-01-01 01:01:01"),
                Timestamp.valueOf("2000-01-01 01:01:01"), "newEntityJson", "entityJson");

        entity = new AuditEntity(1L, "entityType", "operationType", "createdBy",
                "modifiedBy", Timestamp.valueOf("2000-01-01 01:01:01"),
                Timestamp.valueOf("2000-01-01 01:01:01"), "newEntityJson", "entityJson");

    }

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        Mockito.doReturn(Optional.of(entity)).when(repository).findById(dto.getId());
        AuditDto actual = service.findById(dto.getId());
        assertThat(dto).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    void findByIdNegativeTest() {
        Mockito.doReturn(Optional.empty()).when(repository).findById(dto.getId());
        assertThrows(EntityNotFoundException.class, () -> service.findById(dto.getId()));
    }
}