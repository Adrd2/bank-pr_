package com.bank.profile.modal.service;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.AuditEntity;
import com.bank.profile.mapper.AuditMapper;
import com.bank.profile.mapper.AuditMapperImpl;
import com.bank.profile.repository.AuditRepository;
import com.bank.profile.service.AuditService;
import com.bank.profile.service.impl.AuditServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuditServiceTest {
    AuditService service;
    AuditMapper mapper;
    AuditRepository repository;
    AuditDto dto;
    AuditEntity entity;

    @BeforeEach
    public void prepare() {
        entity = new AuditEntity(1L, "entityType", "operationType",
                "createdBy", "modifiedBy", Timestamp.valueOf("2023-06-09 01:01:01"),
                Timestamp.valueOf("2023-06-09 01:01:01"), "newEntityJson", "entityJson");
        repository = Mockito.mock(AuditRepository.class);
        mapper = new AuditMapperImpl();

        service = new AuditServiceImpl(repository, mapper);
        dto = mapper.toDto(entity);
    }

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    public void findByIdPositiveTest() {
        prepare();
        doReturn(Optional.of(entity)).when(repository).findById(entity.getId());
        AuditDto result = service.findById(entity.getId());

        assertAll(
                () -> {
                    assertEquals(dto.getId(), result.getId());
                    assertEquals(dto.getEntityType(), result.getEntityType());
                    assertEquals(dto.getOperationType(), result.getOperationType());
                    assertEquals(dto.getCreatedBy(), result.getCreatedBy());
                    assertEquals(dto.getModifiedBy(), result.getModifiedBy());
                    assertEquals(dto.getCreatedAt(), result.getCreatedAt());
                    assertEquals(dto.getModifiedAt(), result.getModifiedAt());
                    assertEquals(dto.getNewEntityJson(), result.getNewEntityJson());
                    assertEquals(dto.getEntityJson(), result.getEntityJson());

                }
        );
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    public void findByIDNotFoundExceptionNegativeTest() {
        prepare();
        Long id = 123L;
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
    }
}
