package com.bank.profile.modal.controller;

import com.bank.profile.controller.RegistrationController;
import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.repository.RegistrationRepository;
import com.bank.profile.service.RegistrationService;
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

@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest {

    @Autowired
    RegistrationController controller;
    @MockBean
    RegistrationRepository repository;
    @MockBean
    RegistrationService service;
    RegistrationDto dto;
    RegistrationDto dto2;
    List<RegistrationDto> listDto;
    List<Long> list;

    @BeforeEach
    void prepare() {
        dto = new RegistrationDto(1L, "country", "region", "city", "district",
                "locality", "street", "house_number", "house_block",
                "flat_number", 1L);
        dto2 = new RegistrationDto(2L, "country", "region", "city", "district",
                "locality", "street", "house_number", "house_block",
                "flat_number", 1L);
        listDto = new ArrayList<>(Arrays.asList(dto, dto2));
        list = new ArrayList<>(Arrays.asList(1L, 2L));
    }

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() {
        prepare();
        Mockito.when(service.findById(1L)).thenReturn(dto);
        RegistrationDto result = controller.read(1L).getBody();
        assertEquals(dto, result);
    }

    @Test
    @DisplayName("Чтение по id равному null, негативный сценарий")
    void readByIdIsNullNegativeTest() {
        Mockito.when(service.findById(1L)).thenReturn(null);
        ResponseEntity<RegistrationDto> response = controller.read(1L);
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Создание, позитивный сценарий")
    void createPositiveTest() {
        prepare();
        Mockito.when(service.save(dto)).thenReturn(dto);
        RegistrationDto result = controller.create(dto).getBody();
        assertEquals(dto, result);
    }

    @Test
    @DisplayName("Создание null, негативный сценарий")
    void createNegativeTest() {
        Mockito.when(service.save(dto)).thenReturn(null);
        ResponseEntity<RegistrationDto> response = controller.create(dto);
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Обновление, позитивный сценарий")
    void updatePositiveTest() {
        prepare();
        Mockito.when(service.update(1L, dto)).thenReturn(dto);
        ResponseEntity<RegistrationDto> result = controller.update(1L, dto);
        assertEquals(dto, result.getBody());
    }

    @Test
    @DisplayName("Обновление null, негативный сценарий")
    void updateNegativeTest() {
        prepare();
        Mockito.when(service.update(anyLong(), any(RegistrationDto.class))).thenReturn(null);
        ResponseEntity<RegistrationDto> result = controller.update(1L, dto);
        assertNull(result.getBody());
    }

    @Test
    @DisplayName("Чтение по нескольким id, позитивный сценарий")
    void readAllByIdPositiveTest() {
        prepare();
        Mockito.when(service.findAllById(list)).thenReturn(listDto);
        ResponseEntity<List<RegistrationDto>> result = controller.readAllById(list);
        assertEquals(listDto, result.getBody());
    }

    @Test
    @DisplayName("Чтение по пустому списку id, негативный сценарий")
    void readAllByIdNegativeTest() {
        prepare();
        Mockito.when(service.findAllById(anyList())).thenReturn(Collections.emptyList());
        ResponseEntity<List<RegistrationDto>> result = controller.readAllById(list);
        assertTrue(result.getBody().isEmpty());
    }
}
