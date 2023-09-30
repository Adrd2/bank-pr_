package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.service.SuspiciousCardTransferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SuspiciousCardTransferController.class)
class SuspiciousCardTransferControllerTest {
    @MockBean
    SuspiciousCardTransferService service;
    @Autowired
    SuspiciousCardTransferController controller;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    private static SuspiciousCardTransferDto dto;
    private static List<Long> list;
    private static List<SuspiciousCardTransferDto> listDto;

    @BeforeAll
    static void init() {
        dto = new SuspiciousCardTransferDto(1L, 1L, false
                , false, "blockedReason1", "suspicionReason1");
        SuspiciousCardTransferDto dto2 = new SuspiciousCardTransferDto(2L, 2L, false
                , false, "blockedReason2", "suspicionReason2");
        list = new ArrayList<>(Arrays.asList(1L, 2L));
        listDto = new ArrayList<>(Arrays.asList(dto, dto2));
    }

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        Mockito.when(service.findById(1L)).thenReturn(dto);
        mockMvc.perform(get("/suspicious/card/transfer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.blockedReason", equalTo("blockedReason1")));
    }

    @Test
    @DisplayName("Чтение по id, негативный сценарий")
    void readByIdNegative400Test() throws Exception {
        Mockito.when(service.findById(1L)).thenReturn(dto);
        mockMvc.perform(get("/suspicious/card/transfer/"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Чтение всех данных, позитивный сценарий")
    void readAllPositiveTest() throws Exception {
        Mockito.when(service.findAllById(list)).thenReturn(listDto);
        mockMvc.perform(get("/suspicious/card/transfer?ids=1,2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[*].blockedReason"
                        , containsInAnyOrder("blockedReason1", "blockedReason2")));
    }

    @Test
    @DisplayName("Чтение всех данных, негативный сценарий")
    void readAllNegativeTest() throws Exception {
        Mockito.when(service.findAllById(list)).thenReturn(listDto);
        mockMvc.perform(get("/suspicious/card/transfer"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Создание сущности, позитивный сценарий")
    void createPositiveTest() throws Exception {
        Mockito.when(service.save(dto)).thenReturn(dto);
        var request = post("/suspicious/card/transfer/create")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.blockedReason", equalTo("blockedReason1")));
    }

    @Test
    @DisplayName("Создание сущности, негативный сценарий")
    void createNegativeTest() throws Exception {
        Mockito.when(service.save(dto)).thenReturn(dto);
        mockMvc.perform(post("/suspicious/card/transfer/crea"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Обновление сущности, позитивынй сценарий")
    void updatePositiveTest() throws Exception {
        Mockito.when(service.update(1L, dto)).thenReturn(dto);
        var request = put("/suspicious/card/transfer/1")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.blockedReason", equalTo("blockedReason1")));
    }

    @Test
    @DisplayName("Обновление сущности, негативный сценарий")
    void updateNegativeTest() throws Exception {
        Mockito.when(service.update(1L, dto)).thenReturn(dto);
        mockMvc.perform(put("/suspicious/card/transfe"))
                .andExpect(status().is4xxClientError());
    }
}