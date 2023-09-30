package com.bank.account.integration.controller;

import com.bank.account.AccountApplication;
import com.bank.account.controller.AccountAuditController;
import com.bank.account.dto.AuditDto;
import com.bank.account.entity.AuditEntity;
import com.bank.account.repository.AccountAuditRepository;
import com.bank.account.integration.TestContainerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes =
        {AccountApplication.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Testcontainers
class AccountAuditControllerIntegrationTest extends TestContainerConfig {
    @Autowired
    AccountAuditRepository repository;
    @Autowired
    AccountAuditController controller;
    AuditDto dto;
    AuditEntity entity;

    @BeforeEach
    void setUp() {
        entity = new AuditEntity(1L, "entityType", "operationType",
                "createdBy", "modifiedBy",
                Timestamp.valueOf("2000-01-01 01:01:01"),
                Timestamp.valueOf("2001-01-01 01:01:01"),
                "newEntityJson", "entityJson");
        dto = new AuditDto(1L, "entityType", "operationType",
                "createdBy", "modifiedBy",
                Timestamp.valueOf("2000-01-01 01:01:01"),
                Timestamp.valueOf("2001-01-01 01:01:01"),
                "newEntityJson", "entityJson");
        repository.save(entity);
    }

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() {
        Long id = 1L;
        AuditDto result = controller.read(id);
        assertEquals(dto, result);
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeNest() {
        Long id = 123L;
        assertThrows(EntityNotFoundException.class, () -> controller.read(id));
    }
}