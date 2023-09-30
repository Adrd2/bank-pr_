package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.service.LicenseService;
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
import java.util.Arrays;
import java.util.List;

@WebMvcTest(LicenseController.class)
public class LicenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LicenseService service;

    private LicenseDto licenseDto;

    @BeforeEach
    public void setup() {
        licenseDto = new LicenseDto(1L, new Byte[]{1, 2, 3}, null);
    }

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    public void readByIdPositiveTest() throws Exception {
        Mockito.when(service.findById(Mockito.anyLong())).thenReturn(licenseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/license/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    public void readByIdNegativeTest() throws Exception {
        Mockito.when(service.findById(Mockito.anyLong())).thenThrow(new EntityNotFoundException("License not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/license/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    public void readAllByIdPositiveTest() throws Exception {
        List<LicenseDto> licenseDtos = Arrays.asList(
                new LicenseDto(1L, new Byte[]{1, 2, 3}, null),
                new LicenseDto(2L, new Byte[]{4, 5, 6}, null)
        );
        Mockito.when(service.findAllById(Mockito.anyList())).thenReturn(licenseDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/license/read/all")
                        .param("ids", "1,2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2));
    }

    @Test
    @DisplayName("чтение по нескольким несуществующим id, негативный сценарий")
    public void readAllByIdNegativeTest() throws Exception {
        Mockito.when(service.findAllById(Mockito.anyList()))
                .thenThrow(new EntityNotFoundException("Licenses not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/license/read/all")
                        .param("ids", "1,2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("создание сущности, позитивный сценарий")
    public void createPositiveTest() throws Exception {
        Mockito.when(service.create(Mockito.any(LicenseDto.class))).thenReturn(licenseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/license/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"photoLicense\": [1, 2, 3], \"bankDetails\": null}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("создание сущности, негативный сценарий")
    public void createNegativeTest() throws Exception {
        Mockito.when(service.create(Mockito.any(LicenseDto.class)))
                .thenThrow(new EntityNotFoundException("License not created"));

        mockMvc.perform(MockMvcRequestBuilders.post("/license/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"photoLicense\": [1, 2, 3], \"bankDetails\": null}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("обновление сущности, позитивный сценарий")
    public void updatePositiveTest() throws Exception {
        Mockito.when(service.update(Mockito.anyLong(), Mockito.any(LicenseDto.class))).thenReturn(licenseDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/license/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"photoLicense\": [1, 2, 3], \"bankDetails\": null}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("обновление по несуществующему id, негативный сценарий")
    public void updateNegativeTest() throws Exception {
        Mockito.when(service.update(Mockito.anyLong(), Mockito.any(LicenseDto.class)))
                .thenThrow(new EntityNotFoundException("License not updated"));

        mockMvc.perform(MockMvcRequestBuilders.put("/license/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"photoLicense\": [1, 2, 3], \"bankDetails\": null}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}