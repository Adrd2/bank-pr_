package com.bank.antifraud.service.impl;

import com.bank.antifraud.entity.AuditEntity;
import com.bank.antifraud.mappers.AuditMapper;
import com.bank.antifraud.repository.AuditRepository;
import com.bank.antifraud.service.AuditService;
import org.junit.jupiter.api.Assertions;
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

@SpringJUnitConfig(initializers = ConfigDataApplicationContextInitializer.class, classes = {
        AuditServiceImpl.class
})
class AuditServiceImplTest {
    @MockBean
    AuditRepository repository;

    @MockBean
    AuditMapper mapper;

    @Autowired
    AuditService auditService;

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setEntityType("entityTest");
        auditEntity.setOperationType("operationTest");
        auditEntity.setCreatedBy("createdTest");
        auditEntity.setModifiedBy("modifiedTest");
        auditEntity.setCreatedAt(Timestamp.valueOf("2001-01-01 01:01:01"));
        auditEntity.setModifiedAt(Timestamp.valueOf("2001-01-01 01:01:01"));
        auditEntity.setNewEntityJson("newJsonTest");
        auditEntity.setEntityJson("JsonTest");

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(auditEntity));
        Mockito.when(mapper.toDto(auditEntity)).thenReturn(Mockito.any());
        Assertions.assertDoesNotThrow(() -> auditService.findById(1L));
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    void findByIdNegativeTest() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class,
                () -> auditService.findById(1L));

        Assertions.assertEquals("Не найден аудит с ID  1", thrown.getMessage());
    }
}