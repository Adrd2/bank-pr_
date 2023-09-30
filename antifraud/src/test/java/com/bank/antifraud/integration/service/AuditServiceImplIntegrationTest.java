package com.bank.antifraud.integration.service;

import com.bank.antifraud.integration.TestContainer;
import com.bank.antifraud.service.AuditService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityNotFoundException;

@SpringBootTest
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Sql(value = "/create-audit-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/delete-audit-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class AuditServiceImplIntegrationTest extends TestContainer {
    @Autowired
    AuditService auditService;

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        Assertions.assertDoesNotThrow(() -> auditService.findById(1L));
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    void findByIdNegativeTest() {
        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class,
                () -> auditService.findById(3L));

        Assertions.assertEquals("Не найден аудит с ID  3", thrown.getMessage());
    }
}
