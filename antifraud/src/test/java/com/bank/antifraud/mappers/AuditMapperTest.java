package com.bank.antifraud.mappers;

import com.bank.antifraud.entity.AuditEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AuditMapperTest {
    @Spy
    AuditMapper auditMapper = new AuditMapperImpl();

    @Test
    @DisplayName("Маппинг в дто")
    void toDtoTest() {
        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setEntityType("entityTest");
        auditEntity.setOperationType("operationTest");
        auditEntity.setCreatedBy("createdTest");
        auditEntity.setModifiedBy("modifiedTest");
        auditEntity.setCreatedAt(Timestamp.valueOf("2001-01-01 01:01:01"));
        auditEntity.setModifiedAt(Timestamp.valueOf("2001-01-01 01:01:01"));
        auditEntity.setNewEntityJson("newJsonTest");
        auditEntity.setEntityJson("JsonTest");

        var result = auditMapper.toDto(auditEntity);
        assertAll(() -> {
            assertEquals("entityTest", result.getEntityType());
            assertEquals("operationTest", result.getOperationType());
            assertEquals("createdTest", result.getCreatedBy());
            assertEquals("modifiedTest", result.getModifiedBy());
            assertEquals(Timestamp.valueOf("2001-01-01 01:01:01"), result.getCreatedAt());
            assertEquals(Timestamp.valueOf("2001-01-01 01:01:01"), result.getModifiedAt());
            assertEquals("newJsonTest", result.getNewEntityJson());
            assertEquals("JsonTest", result.getEntityJson());
        });
    }

    @Test
    @DisplayName("Маппинг в дто, на вход подан null")
    void toDtoNullTest() {
        assertNull(auditMapper.toDto(null));
    }
}