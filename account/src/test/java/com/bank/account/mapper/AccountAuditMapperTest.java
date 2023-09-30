package com.bank.account.mapper;

import com.bank.account.dto.AuditDto;
import com.bank.account.entity.AuditEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AccountAuditMapperTest {

    @Spy
    AccountAuditMapper mapper = new AccountAuditMapperImpl();
    AuditEntity entity;
    AuditDto dto;

    @BeforeEach
    void prepare() {
        entity = new AuditEntity
                (1L, "entityType", "operationType",
                        "createdBy", "modifiedBy", Timestamp.valueOf("2000-01-01 01:01:01"),
                        Timestamp.valueOf("2001-01-01 01:01:01"),
                        "newEntityJson", "entityJson");
        dto = new AuditDto
                (1L, "entityType", "operationType",
                        "createdBy", "modifiedBy", Timestamp.valueOf("2000-01-01 01:01:01"),
                        Timestamp.valueOf("2001-01-01 01:01:01"),
                        "newEntityJson", "entityJson");
    }

    @Test
    @DisplayName("маппинг в dto")
    void toDtoTest() {
        AuditDto actual = mapper.toDto(entity);

        assertAll(
                () -> {
                    assertEquals(entity.getId(), actual.getId());
                    assertEquals(entity.getEntityType(), actual.getEntityType());
                    assertEquals(entity.getOperationType(), actual.getOperationType());
                    assertEquals(entity.getCreatedBy(), actual.getCreatedBy());
                    assertEquals(entity.getModifiedBy(), actual.getModifiedBy());
                    assertEquals(entity.getCreatedAt(), actual.getCreatedAt());
                    assertEquals(entity.getModifiedAt(), actual.getModifiedAt());
                    assertEquals(entity.getNewEntityJson(), actual.getNewEntityJson());
                    assertEquals(entity.getEntityJson(), actual.getEntityJson());
                }
        );
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан null")
    void toDtoNullTest() {
        AuditDto actual = mapper.toDto(null);
        assertNull(actual);
    }
}