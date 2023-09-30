package com.bank.antifraud.controller;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.service.AuditService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuditController.class)
class AuditControllerTest {
    @MockBean
    AuditService service;
    @Autowired
    AuditController controller;
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        AuditDto dto = new AuditDto(1L, "entityTest", "operationTest"
                , "createdTest", "modifiedTest"
                , Timestamp.valueOf("2001-01-01 01:01:01"), Timestamp.valueOf("2001-01-01 01:01:01")
                , "newJsonTest", "JsonTest");
        Mockito.when(service.findById(1L)).thenReturn(dto);
        mockMvc.perform(get("/audit/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.entityType", equalTo("entityTest")));
    }

    @Test
    @DisplayName("Чтение по id, негативный сценарий")
    void readByIdNegativeTest() throws Exception {
        AuditDto dto = new AuditDto(1L, "entityTest", "operationTest"
                , "createdTest", "modifiedTest"
                , Timestamp.valueOf("2001-01-01 01:01:01"), Timestamp.valueOf("2001-01-01 01:01:01")
                , "newJsonTest", "JsonTest");
        Mockito.when(service.findById(1L)).thenReturn(dto);
        mockMvc.perform(get("/audi"))
                .andExpect(status().is4xxClientError());
    }
}