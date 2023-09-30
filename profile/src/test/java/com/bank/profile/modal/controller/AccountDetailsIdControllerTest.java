package com.bank.profile.modal.controller;

import com.bank.profile.controller.AccountDetailsIdController;
import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.repository.AccountDetailsIdRepository;
import com.bank.profile.service.AccountDetailsIdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

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

@WebMvcTest(AccountDetailsIdController.class)
class AccountDetailsIdControllerTest {

    @Autowired
    AccountDetailsIdController controller;

    @MockBean
    AccountDetailsIdRepository repository;
    @MockBean
    AccountDetailsIdService service;
    @Autowired
    MockMvc mockMvc;
    AccountDetailsIdDto dto;
    AccountDetailsIdDto dto2;
    ProfileDto profile;
    PassportDto passport;
    ActualRegistrationDto actualRegistration;
    RegistrationDto registration;
    List<Long> list;
    List<AccountDetailsIdDto> listDto;

    @BeforeEach
    void prepare() {
        registration = new RegistrationDto(null, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        passport = new PassportDto(null, 1, 2L, "lastname", "firstname",
                "middlename", "gender", LocalDate.now(), "birthPlace",
                "issuedBy", LocalDate.now(), 123, LocalDate.now(), registration);
        actualRegistration = new ActualRegistrationDto(null, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        profile = new ProfileDto(null, 123L, "email", "premium_card",
                123L, 123L, passport, actualRegistration);

        list = new ArrayList<>(Arrays.asList(1L, 2L));
        dto = new AccountDetailsIdDto(null, 1L, profile);
        dto2 = new AccountDetailsIdDto(null, 2L, profile);
        listDto = new ArrayList<>(Arrays.asList(dto, dto2));
    }

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() {
        prepare();
        Mockito.when(service.findById(1L)).thenReturn(dto);
        AccountDetailsIdDto result = controller.read(1L).getBody();
        assertEquals(dto, result);
    }

    @Test
    @DisplayName("Чтение по несуществующему id, негативный сценарий")
    void readByIdIsNullNotFoundExceptionNegativeTest() {
        Mockito.when(service.findById(1L)).thenReturn(null);
        ResponseEntity<AccountDetailsIdDto> response = controller.read(1L);
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Создание, позитивный сценарий")
    void createPositiveTest() {
        prepare();
        Mockito.when(service.save(dto)).thenReturn(dto);
        AccountDetailsIdDto result = controller.create(dto).getBody();
        assertEquals(dto, result);
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
        Mockito.when(service.update(1L, dto)).thenReturn(dto);
        ResponseEntity<AccountDetailsIdDto> result = controller.update(1L, dto);
        assertEquals(dto, result.getBody());
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    void updateNotFoundExceptionNegativeTest() {
        prepare();
        Mockito.when(service.update(anyLong(), any(AccountDetailsIdDto.class))).thenReturn(null);
        ResponseEntity<AccountDetailsIdDto> result = controller.update(1L, dto);
        assertNull(result.getBody());
    }

    @Test
    @DisplayName("Чтение по нескольким id, позитивный сценарий")
    void readAllByIdPositiveTest() {
        prepare();
        Mockito.when(service.findAllById(list)).thenReturn(listDto);
        ResponseEntity<List<AccountDetailsIdDto>> result = controller.readAllById(list);
        assertEquals(listDto, result.getBody());
    }

    @Test
    @DisplayName("Чтение по нескольким несуществующим id, негативный сценарий")
    void readAllByIdNotFoundExceptionNegativeTest() {
        prepare();
        Mockito.when(service.findAllById(anyList())).thenReturn(Collections.emptyList());
        ResponseEntity<List<AccountDetailsIdDto>> result = controller.readAllById(list);
        assertTrue(result.getBody().isEmpty());
    }
}