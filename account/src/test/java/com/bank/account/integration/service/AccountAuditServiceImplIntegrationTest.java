package com.bank.account.integration.service;

import com.bank.account.AccountApplication;
import com.bank.account.dto.AuditDto;
import com.bank.account.entity.AuditEntity;
import com.bank.account.mapper.AccountAuditMapper;
import com.bank.account.repository.AccountAuditRepository;
import com.bank.account.service.AccountAuditService;
import com.bank.account.service.AccountAuditServiceImpl;
import com.bank.account.service.common.ExceptionReturner;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes =
        {AccountApplication.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Testcontainers
class AccountAuditServiceImplIntegrationTest extends TestContainerConfig {
    @Autowired
    AccountAuditMapper mapper;
    @Autowired
    AccountAuditRepository repository;
    @Autowired
    ExceptionReturner returner;
    AccountAuditService service;
    AuditEntity entity;

    @BeforeEach
    public void setUp() {
        service = new AccountAuditServiceImpl(repository, mapper, returner);
        entity = new AuditEntity
                (1L, "entityType", "operationType", "createdBy",
                        "modifiedBy", Timestamp.valueOf("2000-01-01 01:01:01"),
                        Timestamp.valueOf("2001-01-01 01:01:01"),
                        "newEntityJson", "entityJson");
        repository.save(entity);
    }

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    public void findByIdPositiveTest() {
        AuditDto actual = service.findById(entity.getId());
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
    @DisplayName("поиск по несуществующему id, негативный сценарий")
    void findByNonExitIdNegativeTest() {
        Long id = 123L;
        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
    }
}