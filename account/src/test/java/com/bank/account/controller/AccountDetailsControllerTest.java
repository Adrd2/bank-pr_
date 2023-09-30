package com.bank.account.controller;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.feign.ProfileFeignClient;
import com.bank.account.service.AccountDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(AccountDetailsController.class)
@ExtendWith(MockitoExtension.class)
class AccountDetailsControllerTest {
    @MockBean
    ProfileFeignClient feignClient;

    @MockBean
    private AccountDetailsService service;

    @Autowired
    MockMvc mockMvc;

    AccountDetailsDto dto;
    AccountDetailsDto dto2;

    private static List<Long> list;

    @BeforeEach
    void prepare() {
        MockitoAnnotations.openMocks(this);
        dto = new AccountDetailsDto(1L, 85L, 10L,
                2L, BigDecimal.ONE, false, 4L);
        dto2 = new AccountDetailsDto(2L, 86L, 11L,
                3L, BigDecimal.ONE, false, 4L);
        list = new ArrayList<>(Arrays.asList(1L, 2L));
    }


    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        when(service.findById(1L)).thenReturn(dto);
        BDDMockito.given(service.findById(1L)).willReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.
                        get("/details/1")
                        .content(asJsonString(dto))
                        .contentType(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(asJsonString(dto)));
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeNest() throws Exception {
        when(service.findById(123L)).thenReturn(null);
        BDDMockito.given(service.findById(123L)).willReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.
                        get("/details/123")).
                andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    @DisplayName("создание, позитивный сценарий")
    void createPositiveTest() throws Exception {
        when(service.save(dto2)).thenReturn(dto2);
        BDDMockito.given(service.save(dto2)).
                willReturn(dto2);
        mockMvc.perform(MockMvcRequestBuilders.
                        post("/details/create")
                        .content(asJsonString(dto2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(asJsonString(dto2)));
    }

    @Test
    @DisplayName("создание null dto, негативный сценарий")
    void createNullDtoNegativeTest() throws Exception {
        when(service.save(dto)).thenReturn(null);
        BDDMockito.given(service.save(dto)).willReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.
                        post("/details/create")).
                andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        when(service.update(dto.getId(), dto)).thenReturn(dto);
        BDDMockito.given(service.update(dto.getId(), dto)).willReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.
                        put("/details/update/{id}", dto.getId())
                        .content(asJsonString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().string(asJsonString(dto)));
    }

    @Test
    @DisplayName("обновление по несуществующему id, негативный сценарий")
    void updateByNonExistIdNegativeTest() throws Exception {
        when(service.update(123L, dto)).thenReturn(null);
        BDDMockito.given(service.update(123L, dto)).willReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.
                        put("/details/update/123")).
                andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    void readAllPositiveTest() throws Exception {
        when(service.findAllById(List.of(1L))).thenReturn(List.of(dto));
        BDDMockito.given(service.findAllById(List.of(1L))).willReturn(List.of(dto));
        mockMvc.perform(MockMvcRequestBuilders.
                        get("/details/read/all")
                        .param("ids", String.valueOf(1))
                        .content(asJsonString(dto))
                        .contentType(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().
                        string(Collections.singletonList(asJsonString(dto)).toString()));

    }

    @Test
    @DisplayName("чтение по пустому списку id, негативный сценарий")
    void readAllNullIdNegativeTest() throws Exception {
        when(service.findAllById(list)).thenReturn(null);
        BDDMockito.given(service.findAllById(list)).willReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.
                        get("/details/read/all").param("ids", String.valueOf(list))).
                andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}