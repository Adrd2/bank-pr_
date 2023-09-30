package com.bank.account.service;

import com.bank.account.dto.AuditDto;
import com.bank.account.entity.AuditEntity;
import com.bank.account.mapper.AccountAuditMapper;
import com.bank.account.mapper.AccountAuditMapperImpl;
import com.bank.account.repository.AccountAuditRepository;
import com.bank.account.service.common.ExceptionReturner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(initializers = ConfigDataApplicationContextInitializer.class, classes = {
        AccountAuditServiceImpl.class, AccountAuditMapperImpl.class, ExceptionReturner.class
})
class AccountAuditServiceImplTest {
    @MockBean
    AccountAuditRepository repository;
    @Autowired
    AccountAuditService service;
    @Autowired
    AccountAuditMapper mapper;
    @Autowired
    ExceptionReturner exceptionReturner;

    AuditEntity entity;
    AuditDto dto;

    @BeforeEach
    public void setUp() {
        entity = new AuditEntity
                (1L, "entityType", "operationType", "createdBy",
                        "modifiedBy", Timestamp.valueOf("2000-01-01 01:01:01"),
                        Timestamp.valueOf("2001-01-01 01:01:01"),
                        "newEntityJson", "entityJson");
        dto = mapper.toDto(entity);
    }

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    public void findByIdPositiveTest() {
        doReturn(Optional.of(entity)).when(repository).findById(entity.getId());
        AuditDto actual = service.findById(entity.getId());

        assertAll(
                () -> {
                    assertEquals(dto.getId(), actual.getId());
                    assertEquals(dto.getEntityType(), actual.getEntityType());
                    assertEquals(dto.getOperationType(), actual.getOperationType());
                    assertEquals(dto.getCreatedBy(), actual.getCreatedBy());
                    assertEquals(dto.getModifiedBy(), actual.getModifiedBy());
                    assertEquals(dto.getCreatedAt(), actual.getCreatedAt());
                    assertEquals(dto.getModifiedAt(), actual.getModifiedAt());
                    assertEquals(dto.getNewEntityJson(), actual.getNewEntityJson());
                    assertEquals(dto.getEntityJson(), actual.getEntityJson());

                }
        );
    }

    @Test
    @DisplayName("поиск по несуществующему id, негативный сценарий")
    void findByNonExitIdNegativeTest() {
        Long id = 123L;
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
    }
}