package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.service.BranchService;
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
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(BranchController.class)
public class BranchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BranchService service;

    private BranchDto branchDto;

    @BeforeEach
    public void setup() {
        branchDto = new BranchDto(1L, "address", 89998889988L, "City",
                LocalTime.MIN, LocalTime.MAX);
    }

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    public void readByIdPositiveTest() throws Exception {
        Mockito.when(service.findById(Mockito.anyLong())).thenReturn(branchDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/branch/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("address"));
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    public void readByIdNegativeTest() throws Exception {
        Mockito.when(service.findById(Mockito.anyLong())).thenThrow(new EntityNotFoundException("Branch not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/branch/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    public void readAllByIdPositiveTest() throws Exception {
        List<BranchDto> branchDtos = Arrays.asList(
                new BranchDto(1L, "address1", 89998889988L, "City1",
                        LocalTime.MIN, LocalTime.MAX),
                new BranchDto(2L, "address2", 89998889988L, "City2",
                        LocalTime.MIN, LocalTime.MAX)
        );
        Mockito.when(service.findAllById(Mockito.anyList())).thenReturn(branchDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/branch/read/all")
                        .param("ids", "1,2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address").value("address1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].address").value("address2"));
    }

    @Test
    @DisplayName("чтение по нескольким несуществующим id, негативный сценарий")
    public void readAllByIdNegativeTest() throws Exception {
        Mockito.when(service.findAllById(Mockito.anyList()))
                .thenThrow(new EntityNotFoundException("Branches not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/branch/read/all")
                        .param("ids", "1,2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("создание сущности, позитивный сценарий")
    public void createPositiveTest() throws Exception {
        Mockito.when(service.create(Mockito.any(BranchDto.class))).thenReturn(branchDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/branch/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"address\": \"address\", \"phoneNumber\": 89998889988," +
                                " \"city\": \"City\", \"startOfWork\": \"00:00:00\", \"endOfWork\": \"23:59:59\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("address"));
    }

    @Test
    @DisplayName("создание сущности, негативный сценарий")
    public void createNegativeTest() throws Exception {
        Mockito.when(service.create(Mockito.any(BranchDto.class)))
                .thenThrow(new EntityNotFoundException("Branch not created"));

        mockMvc.perform(MockMvcRequestBuilders.post("/branch/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"address\": \"address\", \"phoneNumber\": 89998889988," +
                                " \"city\": \"City\", \"startOfWork\": \"00:00:00\", \"endOfWork\": \"23:59:59\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("обновление сущности, позитивный сценарий")
    public void updatePositiveTest() throws Exception {
        Mockito.when(service.update(Mockito.anyLong(), Mockito.any(BranchDto.class))).thenReturn(branchDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/branch/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"address\": \"address\", \"phoneNumber\": 89998889988," +
                                " \"city\": \"City\", \"startOfWork\": \"00:00:00\", \"endOfWork\": \"23:59:59\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("address"));
    }

    @Test
    @DisplayName("обновление по несуществующему id, негативный сценарий")
    public void updateNegativeTest() throws Exception {
        Mockito.when(service.update(Mockito.anyLong(), Mockito.any(BranchDto.class)))
                .thenThrow(new EntityNotFoundException("Branch not updated"));

        mockMvc.perform(MockMvcRequestBuilders.put("/branch/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"address\": \"address\", \"phoneNumber\": 89998889988," +
                                " \"city\": \"City\", \"startOfWork\": \"00:00:00\", \"endOfWork\": \"23:59:59\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}