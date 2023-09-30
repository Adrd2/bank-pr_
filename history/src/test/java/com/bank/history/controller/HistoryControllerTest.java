package com.bank.history.controller;

import com.bank.history.dto.HistoryDto;
import com.bank.history.service.HistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest(HistoryController.class)
@ExtendWith(MockitoExtension.class)
class HistoryControllerTest {

    @MockBean
    private HistoryService historyService;

    @Autowired
    MockMvc mockMvc;

    HistoryDto dto;
    HistoryDto dto2;

    @BeforeEach
    void prepare() {
        MockitoAnnotations.openMocks(this);
        dto = new HistoryDto(
                1L, 2L, 3L, 4L,
                5L, 6L, 7L);
        dto2 = new HistoryDto(
                11L, 12L, 13L, 14L,
                15L, 16L, 17L);
    }

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        when(historyService.readById(1L)).thenReturn(dto);
        BDDMockito.given(historyService.readById(1L)).willReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/history/1")
                        .content(asJsonString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(asJsonString(dto)));
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeNest() throws Exception {
        when(historyService.readById(123L)).thenReturn(null);
        BDDMockito.given(historyService.readById(123L)).willReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/123"))
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    @DisplayName("создание, позитивный сценарий")
    void createPositiveTest() throws Exception {
        when(historyService.create(any(HistoryDto.class))).thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(dto)));
    }

    @Test
    @DisplayName("создание null dto, негативный сценарий")
    void createNullDtoNegativeTest() throws Exception {
        when(historyService.create(dto)).thenReturn(null);
        BDDMockito.given(historyService.create(dto)).willReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/history"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @DisplayName("обновление по id, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        when(historyService.update(anyLong(), any(HistoryDto.class))).thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/history/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(asJsonString(dto)));
    }

    @Test
    @DisplayName("обновление по несуществующему id, негативный сценарий")
    void updateByNonExistIdNegativeTest() throws Exception {
        when(historyService.update(123L, dto)).thenReturn(null);
        BDDMockito.given(historyService.update(123L, dto)).willReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/history"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    void readAllPositiveTest() throws Exception {
        List<HistoryDto> historyDtoList = Arrays.asList(
                new HistoryDto(1L, 2L, 3L, 4L,
                        5L, 6L, 7L),
                new HistoryDto(2L, 2L, 3L, 4L,
                        5L, 6L, 7L));
        when(historyService.readAllById(anyList())).thenReturn(historyDtoList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/history")
                        .param("id", "1,2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].transferAuditId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].transferAuditId").value(2));
    }

    @Test
    @DisplayName("чтение по нескольким несуществующим id, негативный сценарий")
    void readAllNullIdNegativeTest() throws Exception {
        Mockito.when(historyService.readById(anyLong()))
                .thenThrow(new EntityNotFoundException("ATM not found"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/history/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}