package com.bank.publicinfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.service.BankDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(BankDetailsController.class)
public class BankDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankDetailsService service;

    private Long id;
    private List<Long> ids;
    private BankDetailsDto bankDetailsDto;

    @BeforeEach
    public void setup() {
        id = 1L;
        ids = Arrays.asList(1L, 2L);
        bankDetailsDto = new BankDetailsDto(1L, "041234567", 1234567890L, 123456789L,
                new BigDecimal("30145678901234567890"), "City 1", "ПАО", "Bank 1");
    }

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    public void readByIdPositiveTest() throws Exception {
        Mockito.when(service.findById(id)).thenReturn(bankDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/bank/details/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Bank 1"));

        Mockito.verify(service).findById(id);
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    public void readByIdNegativeTest() throws Exception {
        Mockito.when(service.findById(id)).thenThrow(new EntityNotFoundException("Bank details not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/bank/details/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(service).findById(id);
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    public void readAllByIdPositiveTest() throws Exception {
        List<BankDetailsDto> bankDetailsDtos = Arrays.asList(
                new BankDetailsDto(1L, "041234567", 1234567890L, 123456789L,
                        new BigDecimal("30145678901234567890"), "City 1", "ПАО", "Bank 1"),
                new BankDetailsDto(2L, "041234567", 1234567890L, 123456789L,
                        new BigDecimal("30145678901234567890"), "City 2", "ПАО", "Bank 2")
        );

        Mockito.when(service.findAllById(ids)).thenReturn(bankDetailsDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/bank/details/read/all")
                        .param("ids", "1,2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Bank 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Bank 2"));

        Mockito.verify(service).findAllById(ids);
    }

    @Test
    @DisplayName("чтение по нескольким несуществующим id, негативный сценарий")
    public void readAllByIdNegativeTest() throws Exception {
        Mockito.when(service.findAllById(ids)).thenThrow(new EntityNotFoundException("Bank details not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/bank/details/read/all")
                        .param("ids", "1,2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(service).findAllById(ids);
    }

    @Test
    @DisplayName("создание сущности, позитивный сценарий")
    public void createPositiveTest() throws Exception {
        Mockito.when(service.create(bankDetailsDto)).thenReturn(bankDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/bank/details/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bankDetailsDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Bank 1"));

        Mockito.verify(service).create(bankDetailsDto);
    }

    @Test
    @DisplayName("создание сущности, негативный сценарий")
    public void createNegativeTest() throws Exception {
        Mockito.when(service.create(bankDetailsDto)).thenThrow(new EntityNotFoundException("Bank details not created"));

        mockMvc.perform(MockMvcRequestBuilders.post("/bank/details/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bankDetailsDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(service).create(bankDetailsDto);
    }

    @Test
    @DisplayName("обновление сущности, позитивный сценарий")
    public void updatePositiveTest() throws Exception {
        Mockito.when(service.update(id, bankDetailsDto)).thenReturn(bankDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/bank/details/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bankDetailsDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Bank 1"));

        Mockito.verify(service).update(id, bankDetailsDto);
    }

    @Test
    @DisplayName("обновление по несуществующему id, негативный сценарий")
    public void updateNegativeTest() throws Exception {
        Mockito.when(service.update(id, bankDetailsDto)).
                thenThrow(new EntityNotFoundException("Bank details not updated"));

        mockMvc.perform(MockMvcRequestBuilders.put("/bank/details/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bankDetailsDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(service).update(id, bankDetailsDto);
    }
}