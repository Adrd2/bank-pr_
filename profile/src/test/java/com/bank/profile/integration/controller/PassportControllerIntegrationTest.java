package com.bank.profile.integration.controller;

import com.bank.profile.controller.PassportController;
import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.mapper.PassportMapper;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.service.PassportService;
import org.apache.http.protocol.HTTP;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;

@WebMvcTest(PassportController.class)
public class PassportControllerIntegrationTest {

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
    PassportMapper mapper;
    PassportEntity entity;
    Long id;

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
        mapper = mock(PassportMapper.class);
        entity = mapper.toEntity(dto);
        repository.save(entity);
        id = 1L;
    }

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() {
        prepare();
        ResponseEntity<PassportDto> result = controller.read(id);
        assertEquals(dto, result.getBody());
    }

    @Test
    @DisplayName("Чтение по несуществующему id, негативный сценарий")
    void readByIdIsNullNegativeTest() {
        ResponseEntity<PassportDto> response = controller.read(100L);
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Создание, позитивный сценарий")
    void createPositiveTest() {
        prepare();
        ResponseEntity<PassportDto> result = controller.create(dto);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("Создание null, негативный сценарий")
    void createNullNegativeTest() {
        ResponseEntity<PassportDto> response = controller.create(null);
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updatePositiveTest() {
        prepare();
        ResponseEntity<PassportDto> result = controller.update(1L, dto);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    void updateNotFoundExceptionNegativeTest() {
        prepare();
        ResponseEntity<PassportDto> result = controller.update(100L, dto);
        assertNull(result.getBody());
    }

    @Test
    @DisplayName("Чтение по нескольким id, позитивный сценарий")
    void readAllByIdPositiveTest() {
        prepare();
        ResponseEntity<List<PassportDto>> result = controller.readAllById(list);
        assertEquals(List.of(dto), result.getBody());
    }

    @Test
    @DisplayName("Чтение по нескольким несуществующим id, негативный сценарий")
    void readAllByIdNotFoundExceptionNegativeTest() {
        prepare();
        ResponseEntity<List<PassportDto>> result = controller.readAllById(List.of(100L));
        assertTrue(result.getBody().isEmpty());
    }

}


