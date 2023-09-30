package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.entity.AuditEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertNull;

class AuditMapperTest {
    private AuditDto auditDto;
    private AuditEntity auditEntity;
    private AuditMapper auditMapper;

    @BeforeEach
    void prepare() {
        auditMapper = new AuditMapperImpl();
        auditEntity = new AuditEntity(1L, "entityType",
                "operationType", "createdBy",
                "modifiedBy", Timestamp.valueOf("2000-01-01 01:01:01"),
                Timestamp.valueOf("2001-01-01 01:01:01"), "newEntityJson",
                "entityJson");
        auditDto = new AuditDto(1L, "entityType",
                "operationType", "createdBy",
                "modifiedBy", Timestamp.valueOf("2000-01-01 01:01:01"),
                Timestamp.valueOf("2001-01-01 01:01:01"),
                "newEntityJson", "entityJson");
    }

    @Test
    @DisplayName("маппинг в дто")
    void toDtoTest() {
        AuditDto actual = auditMapper.toDto(auditEntity);
        assertThat(auditDto).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    @DisplayName("маппинг в дто, на вход подан null")
    void toDtoIfNullTest() {
        assertNull(auditMapper.toDto(null));
    }
}