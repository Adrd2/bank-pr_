package com.bank.profile.integration.controller;

import com.bank.profile.controller.AccountDetailsIdController;
import com.bank.profile.dto.*;
import com.bank.profile.entity.*;
import com.bank.profile.integration.ContainerConfig;
import com.bank.profile.mapper.AccountDetailsIdMapper;
import com.bank.profile.repository.AccountDetailsIdRepository;
import com.bank.profile.service.AccountDetailsIdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@Testcontainers
@WebMvcTest(AccountDetailsIdController.class)
class AccountDetailsIdControllerIntegrationTest extends ContainerConfig {

    @Autowired
    AccountDetailsIdController controller;

    @MockBean
    AccountDetailsIdRepository repository;
    @MockBean
    AccountDetailsIdService service;
    @Autowired
    MockMvc mockMvc;
    AccountDetailsIdDto dto;
    ProfileDto profile;
    PassportDto passport;
    ActualRegistrationDto actualRegistration;
    RegistrationDto registration;
    List<Long> list;
    List<AccountDetailsIdDto> listDto;
    AccountDetailsIdEntity entity;
    AccountDetailsIdMapper mapper;

    Long id;

    @BeforeEach
    void prepare() {
        id = 1L;

        MockitoAnnotations.openMocks(this);
        registration = new RegistrationDto(1L, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        passport = new PassportDto(1L, 1, 2L, "lastname", "firstname",
                "middlename", "gender", LocalDate.now(), "birthPlace",
                "issuedBy", LocalDate.now(), 123, LocalDate.now(), registration);
        actualRegistration = new ActualRegistrationDto(1L, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        profile = new ProfileDto(1L, 123L, "email", "premium_card",
                123L, 123L, passport, actualRegistration);


        dto = new AccountDetailsIdDto(1L, 1L, profile);
        mapper = mock(AccountDetailsIdMapper.class);
        list = new ArrayList<>(List.of(1L));
        entity = mapper.toEntity(dto);
        listDto = new ArrayList<>(Arrays.asList(dto));
        repository.save(entity);
    }

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() {
        prepare();
        Long id = 1L;
        ResponseEntity<AccountDetailsIdDto> result = controller.read(id);
        assertEquals(dto, result.getBody());

    }

    @Test
    @DisplayName("Чтение по несуществующему id, негативный сценарий")
    void readByIdIsNullNotFoundExceptionNegativeTest() {
        ResponseEntity<AccountDetailsIdDto> response = controller.read(null);
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Создание, позитивный сценарий")
    void createPositiveTest() {
        prepare();
        ResponseEntity<AccountDetailsIdDto> result = controller.create(dto);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("Создание null, негативный сценарий")
    void createNullNegativeTest() {
        Mockito.when(service.save(dto)).thenReturn(null);
        ResponseEntity<AccountDetailsIdDto> response = controller.create(dto);
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updatePositiveTest() {
        prepare();
        ResponseEntity<AccountDetailsIdDto> result = controller.update(id, dto);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    void updateNotFoundExceptionNegativeTest() {
        prepare();
        ResponseEntity<AccountDetailsIdDto> result = controller.update(1L, dto);
        assertNull(result.getBody());
    }

    @Test
    @DisplayName("Чтение по нескольким id, позитивный сценарий")
    void readAllByIdPositiveTest() {
        prepare();
        ResponseEntity<List<AccountDetailsIdDto>> result = controller.readAllById(list);
        assertEquals(List.of(dto), result.getBody());
    }

    @Test
    @DisplayName("Чтение по нескольким несуществующим id, негативный сценарий")
    void readAllByIdNotFoundExceptionNegativeTest() {
        prepare();
        ResponseEntity<List<AccountDetailsIdDto>> result = controller.readAllById(list);
        assertTrue(result.getBody().isEmpty());
    }
}