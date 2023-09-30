package com.bank.antifraud.integration.controller;

import com.bank.antifraud.controller.SuspiciousPhoneTransferController;
import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.integration.TestContainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Sql(value = "/create-suspicious-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/delete-suspicious-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class SuspiciousPhoneTransferControllerIntegrationTest extends TestContainer {
    @Autowired
    SuspiciousPhoneTransferController controller;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    private static SuspiciousPhoneTransferDto dto;

    @BeforeAll
    static void init() {
        dto = new SuspiciousPhoneTransferDto(3L, 3L
                , false, false
                , "blockedReason3", "suspicionReason3");
    }

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        mockMvc.perform(get("/suspicious/phone/transfer/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(4)))
                .andExpect(jsonPath("$.blockedReason", equalTo("blockedReason4")));
    }

    @Test
    @DisplayName("Чтение по id, негативный сценарий")
    void readByIdNegativeTest() throws Exception {
        mockMvc.perform(get("/suspicious/phone/transfer/"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Чтение всех данных, позитивный сценарий")
    void readAllPositiveTest() throws Exception {
        mockMvc.perform(get("/suspicious/phone/transfer?ids=4,2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(4, 2)))
                .andExpect(jsonPath("$[*].blockedReason"
                        , containsInAnyOrder("blockedReason4", "blockedReason2")));
    }

    @Test
    @DisplayName("Чтение всех данных, негативный сценарий")
    void readAllNegativeTest() throws Exception {
        mockMvc.perform(get("/suspicious/phone/transfer"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("Создание сущности, позитивный сценарий")
    void createPositiveTest() throws Exception {
        var request = post("/suspicious/phone/transfer/create")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.blockedReason", equalTo("blockedReason3")));
    }

    @Test
    @DisplayName("Создание сущности, негативный сценарий")
    void createNegativeTest() throws Exception {
        mockMvc.perform(post("/suspicious/phone/transfer/crea"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Обновление сущности, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        var request = put("/suspicious/phone/transfer/4")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(4)))
                .andExpect(jsonPath("$.blockedReason", equalTo("blockedReason3")));
    }

    @Test
    @DisplayName("Обновление сущности, негативный сценарий")
    void updateNegativeTest() throws Exception {
        mockMvc.perform(put("/suspicious/phone/transfe"))
                .andExpect(status().is4xxClientError());
    }
}
