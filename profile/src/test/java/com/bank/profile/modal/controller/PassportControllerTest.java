package com.bank.profile.modal.controller;

import com.bank.profile.controller.PassportController;
import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.service.PassportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
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

@WebMvcTest(PassportController.class)
public class PassportControllerTest {

    @Autowired
    PassportController controller;
    @MockBean
    PassportRepository repository;
    @MockBean
    PassportService service;
    PassportDto dto;
    PassportDto dto2;
    List<PassportDto> listDto;
    List<Long> list;
    RegistrationDto registration;

    @BeforeEach
    void prepare() {
        dto = new PassportDto(1L, 1, 2L, "lastname", "firstname",
                "middlename", "gender", LocalDate.now(), "birthPlace",
                "issuedBy", LocalDate.now(), 123, LocalDate.now(), registration);
        dto2 = new PassportDto(2L, 1, 2L, "lastname", "firstname",
                "middlename", "gender", LocalDate.now(), "birthPlace",
                "issuedBy", LocalDate.now(), 123, LocalDate.now(), registration);
        list = new ArrayList<>(Arrays.asList(1L, 2L));
        listDto = new ArrayList<>(Arrays.asList(dto, dto2));
    }

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() {
        prepare();
        Mockito.when(service.findById(1L)).thenReturn(dto);
        PassportDto result = controller.read(1L).getBody();
        assertEquals(dto, result);
    }

    @Test
    @DisplayName("Чтение по несуществующему id, негативный сценарий")
    void readByIdIsNullNegativeTest() {
        Mockito.when(service.findById(1L)).thenReturn(null);
        ResponseEntity<PassportDto> response = controller.read(1L);
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Создание, позитивный сценарий")
    void createPositiveTest() {
        prepare();
        Mockito.when(service.save(dto)).thenReturn(dto);
        PassportDto result = controller.create(dto).getBody();
        assertEquals(dto, result);
    }

    @Test
    @DisplayName("Создание null, негативный сценарий")
    void createNullNegativeTest() {
        Mockito.when(service.save(dto)).thenReturn(null);
        ResponseEntity<PassportDto> response = controller.create(dto);
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updatePositiveTest() {
        prepare();
        Mockito.when(service.update(1L, dto)).thenReturn(dto);
        ResponseEntity<PassportDto> result = controller.update(1L, dto);
        assertEquals(dto, result.getBody());
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    void updateNotFoundExceptionNegativeTest() {
        prepare();
        Mockito.when(service.update(anyLong(), any(PassportDto.class))).thenReturn(null);
        ResponseEntity<PassportDto> result = controller.update(1L, dto);
        assertNull(result.getBody());
    }

    @Test
    @DisplayName("Чтение по нескольким id, позитивный сценарий")
    void readAllByIdPositiveTest() {
        prepare();
        Mockito.when(service.findAllById(list)).thenReturn(listDto);
        ResponseEntity<List<PassportDto>> result = controller.readAllById(list);
        assertEquals(listDto, result.getBody());
    }

    @Test
    @DisplayName("Чтение по нескольким несуществующим id, негативный сценарий")
    void readAllByIdNotFoundExceptionNegativeTest() {
        prepare();
        Mockito.when(service.findAllById(anyList())).thenReturn(Collections.emptyList());
        ResponseEntity<List<PassportDto>> result = controller.readAllById(list);
        assertTrue(result.getBody().isEmpty());
    }

}


