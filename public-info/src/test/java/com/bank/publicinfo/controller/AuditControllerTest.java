package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.service.AuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityNotFoundException;

@WebMvcTest(AuditController.class)
public class AuditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditService service;

    private AuditDto auditDto;

    @BeforeEach
    public void setup() {
        auditDto = new AuditDto(1L, "entityType", "operationType", "createdBy",
                "modifiedBy", null, null, "newEntityJson", "entityJson");
    }

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    public void readPositiveTest() throws Exception {
        Mockito.when(service.findById(Mockito.anyLong())).thenReturn(auditDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/audit/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.entityType").value("entityType"));
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    public void readNegativeTest() throws Exception {
        Mockito.when(service.findById(Mockito.anyLong())).thenThrow(new EntityNotFoundException("Audit not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/audit/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}