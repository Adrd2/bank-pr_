package com.bank.antifraud.integration.mapper;

import com.bank.antifraud.entity.AuditEntity;
import com.bank.antifraud.integration.TestContainer;
import com.bank.antifraud.mappers.AuditMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class AuditMapperIntegrationTest extends TestContainer {
    @Autowired
    AuditMapper auditMapper;

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
