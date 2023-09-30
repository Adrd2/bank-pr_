package com.bank.profile.modal.controller;

import com.bank.profile.controller.ActualRegistrationController;
import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.repository.ActualRegistrationRepository;
import com.bank.profile.service.ActualRegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(ActualRegistrationController.class)
public class ActualRegistrationControllerTest {
    @Autowired
    ActualRegistrationController controller;
    @MockBean
    ActualRegistrationRepository repository;
    @MockBean
    ActualRegistrationService service;
    ActualRegistrationDto dto;
    ActualRegistrationDto dto2;
    List<ActualRegistrationDto> listDto;
    List<Long> list;

    @BeforeEach
    void prepare() {
        dto = new ActualRegistrationDto(1L, "country", "region", "city", "district",
                "locality", "street", "house_number", "house_block",
                "flat_number", 1L);
        dto2 = new ActualRegistrationDto(2L, "country", "region", "city", "district",
                "locality", "street", "house_number", "house_block",
                "flat_number", 1L);
        list = new ArrayList<>(Arrays.asList(1L, 2L));
        listDto = new ArrayList<>(Arrays.asList(dto, dto2));
    }

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() {
        prepare();
        Mockito.when(service.findById(1L)).thenReturn(dto);
        ActualRegistrationDto result = controller.read(1L).getBody();
        assertEquals(dto, result);
    }

    @Test
    @DisplayName("Чтение по несуществующему id, негативный сценарий")
    void readByIdIsNullNotFoundExceptionNegativeTest() {
        Mockito.when(service.findById(1L)).thenReturn(null);
        ResponseEntity<ActualRegistrationDto> response = controller.read(1L);
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Создание, позитивный сценарий")
    void createPositiveTest() {
        prepare();
        Mockito.when(service.save(dto)).thenReturn(dto);
        ActualRegistrationDto result = controller.create(dto).getBody();
        assertEquals(dto, result);
    }

    @Test
    @DisplayName("Создание null, негативный сценарий")
    void createNullNegativeTest() {
        Mockito.when(service.save(dto)).thenReturn(null);
        ResponseEntity<ActualRegistrationDto> response = controller.create(dto);
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updatePositiveTest() {
        prepare();
        Mockito.when(service.update(1L, dto)).thenReturn(dto);
        ResponseEntity<ActualRegistrationDto> result = controller.update(1L, dto);
        assertEquals(dto, result.getBody());
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    void updateNotFoundExceptionNegativeTest() {
        prepare();
        Mockito.when(service.update(anyLong(), any(ActualRegistrationDto.class))).thenReturn(null);
        ResponseEntity<ActualRegistrationDto> result = controller.update(1L, dto);
        assertNull(result.getBody());
    }

    @Test
    @DisplayName("Чтение по нескольким id, позитивный сценарий")
    void readAllByIdPositiveTest() {
        prepare();
        Mockito.when(service.findAllById(list)).thenReturn(listDto);
        ResponseEntity<List<ActualRegistrationDto>> result = controller.readAllById(list);
        assertEquals(listDto, result.getBody());
    }

    @Test
    @DisplayName("Чтение по нескольким несуществующим id, негативный сценарий")
    void readAllByIdNotFoundExceptionNegativeTest() {
        prepare();
        Mockito.when(service.findAllById(anyList())).thenReturn(Collections.emptyList());
        ResponseEntity<List<ActualRegistrationDto>> result = controller.readAllById(list);
        assertTrue(result.getBody().isEmpty());
    }
}