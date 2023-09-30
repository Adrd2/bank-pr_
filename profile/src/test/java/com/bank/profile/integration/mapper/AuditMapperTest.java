package com.bank.profile.integration.mapper;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.AuditEntity;
import com.bank.profile.mapper.AuditMapper;
import com.bank.profile.mapper.AuditMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class AuditMapperTest {

    AuditEntity entity;
    AuditDto dto;
    AuditMapper mapper;

    @BeforeEach
    void prepare() {
        entity = new AuditEntity(1L, "entityType", "operationType",
                "createdBy", "modifiedBy",
                Timestamp.valueOf("2023-06-09 01:01:01"),
                Timestamp.valueOf("2023-06-09 01:01:01"),
                "newEntityJson", "entityJson");
        mapper = new AuditMapperImpl();
        dto = mapper.toDto(entity);
    }

    @Test
    @DisplayName("Маппинг в dto")
    void toDtoTest() {
        AuditDto result = mapper.toDto(entity);

        assertAll(
                () -> {
                    assertEquals(entity.getId(), result.getId());
                    assertEquals(entity.getEntityJson(), result.getEntityJson());
                    assertEquals(entity.getNewEntityJson(), result.getNewEntityJson());
                    assertEquals(entity.getCreatedAt(), result.getCreatedAt());
                    assertEquals(entity.getCreatedBy(), result.getCreatedBy());
                    assertEquals(entity.getModifiedAt(), result.getModifiedAt());
                    assertEquals(entity.getModifiedBy(), result.getModifiedBy());
                    assertEquals(entity.getEntityType(), result.getEntityType());
                    assertEquals(entity.getOperationType(), result.getOperationType());
                }
        );

    }

    @Test
    @DisplayName("Маппинг в dto, на вход подается null")
    void toDtoNullTest() {
        assertNull(mapper.toDto(null));
    }

}
