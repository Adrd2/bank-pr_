package com.bank.profile.integration.controller;

import com.bank.profile.controller.ActualRegistrationController;
import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.mapper.ActualRegistrationMapper;
import com.bank.profile.repository.ActualRegistrationRepository;
import com.bank.profile.service.ActualRegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;

@WebMvcTest(ActualRegistrationController.class)
public class ActualRegistrationControllerIntegrationTest {
    @Autowired
    ActualRegistrationController controller;
    @MockBean
    ActualRegistrationRepository repository;
    @MockBean
    ActualRegistrationService service;
    ActualRegistrationDto dto;
    ActualRegistrationDto dto2;
    List<ActualRegistrationDto> listDto;
    ActualRegistrationEntity entity;
    ActualRegistrationMapper mapper;
    List<Long> list;

    @BeforeEach
    void prepare() {
        dto = new ActualRegistrationDto(1L, "country", "region", "city", "district",
                "locality", "street", "house_number", "house_block",
                "flat_number", 1L);
        list = new ArrayList<>(Arrays.asList(1L, 2L));
        listDto = new ArrayList<>(Arrays.asList(dto));
        mapper = mock(ActualRegistrationMapper.class);

        entity = mapper.toEntity(dto);
        repository.save(entity);
    }

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() {
        prepare();
        Long id = 1L;
        ActualRegistrationDto result = controller.read(id).getBody();
        assertEquals(dto, result);
    }

    @Test
    @DisplayName("Чтение по несуществующему id, негативный сценарий")
    void readByIdIsNullNotFoundExceptionNegativeTest() {
        ResponseEntity<ActualRegistrationDto> response = controller.read(100L);
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Создание, позитивный сценарий")
    void createPositiveTest() {
        prepare();
        ResponseEntity<ActualRegistrationDto> result = controller.create(dto);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("Создание null, негативный сценарий")
    void createNullNegativeTest() {
        ResponseEntity<ActualRegistrationDto> response = controller.create(null);
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updatePositiveTest() {
        prepare();
        ResponseEntity<ActualRegistrationDto> result = controller.update(1L, dto);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    void updateNotFoundExceptionNegativeTest() {
        prepare();
        ResponseEntity<ActualRegistrationDto> result = controller.update(1L, dto);
        assertNull(result.getBody());
    }

    @Test
    @DisplayName("Чтение по нескольким id, позитивный сценарий")
    void readAllByIdPositiveTest() {
        prepare();
        List<ActualRegistrationDto> result = controller.readAllById(list).getBody();
        assertEquals(List.of(dto), result);
    }

    @Test
    @DisplayName("Чтение по нескольким несуществующим id, негативный сценарий")
    void readAllByIdNotFoundExceptionNegativeTest() {
        prepare();
        ResponseEntity<List<ActualRegistrationDto>> result = controller.readAllById(null);
        assertTrue(result.getBody().isEmpty());
    }
}