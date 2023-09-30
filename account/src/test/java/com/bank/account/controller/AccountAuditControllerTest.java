package com.bank.account.controller;

import com.bank.account.dto.AuditDto;
import com.bank.account.service.AccountAuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Timestamp;

import static org.mockito.Mockito.when;

@WebMvcTest(AccountAuditController.class)
@ExtendWith(MockitoExtension.class)
class AccountAuditControllerTest {

    @MockBean
    private AccountAuditService service;

    @Autowired
    MockMvc mockMvc;

    AuditDto dto;

    @BeforeEach
    void setUp() {
        dto = new AuditDto(1L, "entityType", "operationType", 
                "createdBy", "modifiedBy",
                Timestamp.valueOf("2000-01-01 01:01:01"),
                Timestamp.valueOf("2001-01-01 01:01:01"),
                "newEntityJson", "entityJson");

    }

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        when(service.findById(1L)).thenReturn(dto);
        BDDMockito.given(service.findById(1L)).willReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.
                        get("/audit/1")).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string("{\"id\":1," +
                        "\"entityType\":\"entityType\",\"operationType\":\"operationType\"," +
                        "\"createdBy\":\"createdBy\",\"modifiedBy\":\"modifiedBy\"," +
                        "\"createdAt\":\"1999-12-31T22:01:01.000+00:00\"," +
                        "\"modifiedAt\":\"2000-12-31T22:01:01.000+00:00\"," +
                        "\"newEntityJson\":\"newEntityJson\",\"entityJson\":\"entityJson\"}"
                ));
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeNest() throws Exception {
        when(service.findById(123L)).thenReturn(null);
        BDDMockito.given(service.findById(123L)).willReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.
                        get("/audit/123")).
                andExpect(MockMvcResultMatchers.content().string(""));
    }
}