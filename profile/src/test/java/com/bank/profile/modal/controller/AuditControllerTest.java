package com.bank.profile.modal.controller;

import com.bank.profile.controller.AuditController;
import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.AuditEntity;
import com.bank.profile.mapper.AuditMapper;
import com.bank.profile.mapper.AuditMapperImpl;
import com.bank.profile.repository.AuditRepository;
import com.bank.profile.service.AuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@WebMvcTest(AuditController.class)
public class AuditControllerTest {
    @Autowired
    AuditController controller;
    @MockBean
    AuditRepository repository;
    @MockBean
    AuditService service;
    AuditEntity entity;
    AuditEntity entity2;
    AuditMapper mapper;
    AuditDto dto;
    AuditDto dto2;
    List<Long> list;
    List<AuditDto> listDto;

    @BeforeEach
    void prepare() {
        entity = new AuditEntity(1L, "entityType", "operationType", "createdBy",
                "modifiedBy", Timestamp.valueOf("2023-06-09 01:01:01"),
                Timestamp.valueOf("2023-06-09 01:01:01"), "newEntityJson", "entityJson");
        entity2 = new AuditEntity(2L, "entityType", "operationType", "createdBy",
                "modifiedBy", Timestamp.valueOf("2023-06-09 01:01:01"),
                Timestamp.valueOf("2023-06-09 01:01:01"), "newEntityJson", "entityJson");
        mapper = new AuditMapperImpl();
        list = new ArrayList<>(Arrays.asList(1L, 2L));
        dto = mapper.toDto(entity);
        dto2 = mapper.toDto(entity2);
        listDto = new ArrayList<>(Arrays.asList(dto, dto2));
    }

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readPositiveTest() {
        prepare();
        Mockito.when(service.findById(1L)).thenReturn(dto);
        AuditDto result = controller.read(1L);
        assertEquals(dto, result);
    }

    @Test
    @DisplayName("Чтение по несуществующему id, негативный сценарий")
    void readNotFoundExceptionNegativeTest() {
        prepare();
        Mockito.when(service.findById(1L)).thenReturn(null);
        AuditDto result = controller.read(1L);
        assertNull(result);
    }

}
