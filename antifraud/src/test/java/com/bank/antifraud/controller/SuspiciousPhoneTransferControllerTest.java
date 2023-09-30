package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.service.SuspiciousPhoneTransferService;
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

@WebMvcTest(SuspiciousPhoneTransferController.class)
class SuspiciousPhoneTransferControllerTest {
    @MockBean
    SuspiciousPhoneTransferService service;
    @Autowired
    SuspiciousPhoneTransferController controller;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    private static SuspiciousPhoneTransferDto dto;
    private static List<Long> list;
    private static List<SuspiciousPhoneTransferDto> listDto;

    @BeforeAll
    static void init() {
        dto = new SuspiciousPhoneTransferDto(1L, 1L, false
                , false, "blockedReason1", "suspicionReason1");
        SuspiciousPhoneTransferDto dto2 = new SuspiciousPhoneTransferDto(2L, 2L, false
                , false, "blockedReason2", "suspicionReason2");
        list = new ArrayList<>(Arrays.asList(1L, 2L));
        listDto = new ArrayList<>(Arrays.asList(dto, dto2));
    }

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        Mockito.when(service.findById(1L)).thenReturn(dto);
        mockMvc.perform(get("/suspicious/phone/transfer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.blockedReason", equalTo("blockedReason1")));
    }

    @Test
    @DisplayName("Чтение по id, негативный сценарий")
    void readByIdNegative400Test() throws Exception {
        Mockito.when(service.findById(1L)).thenReturn(dto);
        mockMvc.perform(get("/suspicious/phone/transfer/"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Чтение всех данных, позитивный сценарий")
    void readAllPositiveTest() throws Exception {
        Mockito.when(service.findAllById(list)).thenReturn(listDto);
        mockMvc.perform(get("/suspicious/phone/transfer?ids=1,2"))
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
        mockMvc.perform(get("/suspicious/phone/transfer"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Создание сущности, позитивный сценарий")
    void createPositiveTest() throws Exception {
        Mockito.when(service.save(dto)).thenReturn(dto);
        var request = post("/suspicious/phone/transfer/create")
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
        mockMvc.perform(post("/suspicious/phone/transfer/crea"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Обновление сущности, позитивынй сценарий")
    void updatePositiveTest() throws Exception {
        Mockito.when(service.update(1L, dto)).thenReturn(dto);
        var request = put("/suspicious/phone/transfer/1")
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
        mockMvc.perform(put("/suspicious/phone/transfe"))
                .andExpect(status().is4xxClientError());
    }
}