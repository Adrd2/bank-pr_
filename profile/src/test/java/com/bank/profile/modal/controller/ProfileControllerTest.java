package com.bank.profile.modal.controller;

import com.bank.profile.controller.ProfileController;
import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.ProfileMapper;
import com.bank.profile.mapper.ProfileMapperImpl;
import com.bank.profile.repository.ProfileRepository;
import com.bank.profile.service.ProfileService;
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

@WebMvcTest(ProfileController.class)
public class ProfileControllerTest {

    @Autowired
    ProfileController controller;
    @MockBean
    ProfileRepository repository;
    @MockBean
    ProfileService service;
    ProfileDto dto;
    ProfileDto dto2;
    List<ProfileDto> listDto;
    ProfileMapper mapper;
    List<Long> list;
    RegistrationEntity registration;
    RegistrationDto registrationDto;
    PassportEntity passport;
    PassportDto passportDto;
    ActualRegistrationDto actualRegistrationDto;

    @BeforeEach
    void prepare() {
        registrationDto = new RegistrationDto(null, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        passport = new PassportEntity(null, 1, 2L, "lastname",
                "firstname", "middlename", "gender", LocalDate.now(),
                "birthPlace", "issuedBy", LocalDate.now(), 123,
                LocalDate.now(), registration);
        passportDto = new PassportDto(null, 1, 2L, "lastname", "firstname",
                "middlename", "gender", LocalDate.now(), "birthPlace",
                "issuedBy", LocalDate.now(), 123, LocalDate.now(), registrationDto);
        actualRegistrationDto = new ActualRegistrationDto(null, "country", "region",
                "city", "district", "locality", "street",
                "house_number", "house_block", "flat_number", 1L);
        mapper = new ProfileMapperImpl();
        dto = new ProfileDto(1L, 1L, "email", "nameOnCard", 1L, 1L,
                passportDto, actualRegistrationDto);
        dto2 = new ProfileDto(2L, 1L, "email", "nameOnCard", 1L, 1L,
                passportDto, actualRegistrationDto);
        listDto = new ArrayList<>(Arrays.asList(dto, dto2));
        list = new ArrayList<>(Arrays.asList(1L, 2L));
    }

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() {
        prepare();
        Mockito.when(service.findById(1L)).thenReturn(dto);
        ProfileDto result = controller.read(1L).getBody();
        assertEquals(dto, result);
    }

    @Test
    @DisplayName("Чтение по несуществующему id, негативный сценарий")
    void readByIdIsNullNotFoundExceptionNegativeTest() {
        Mockito.when(service.findById(1L)).thenReturn(null);
        ResponseEntity<ProfileDto> response = controller.read(1L);
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Создание, позитивный сценарий")
    void createPositiveTest() {
        prepare();
        Mockito.when(service.save(dto)).thenReturn(dto);
        ProfileDto result = controller.create(dto).getBody();
        assertEquals(dto, result);
    }

    @Test
    @DisplayName("Создание null, негативный сценарий")
    void createNullNegativeTest() {
        Mockito.when(service.save(dto)).thenReturn(null);
        ResponseEntity<ProfileDto> response = controller.create(dto);
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updatePositiveTest() {
        prepare();
        Mockito.when(service.update(1L, dto)).thenReturn(dto);
        ResponseEntity<ProfileDto> result = controller.update(1L, dto);
        assertEquals(dto, result.getBody());
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    void updateNotFoundExceptionNegativeTest() {
        prepare();
        Mockito.when(service.update(anyLong(), any(ProfileDto.class))).thenReturn(null);
        ResponseEntity<ProfileDto> result = controller.update(1L, dto);
        assertNull(result.getBody());
    }

    @Test
    @DisplayName("Чтение по нескольким id, позитивный сценарий")
    void readAllByIdPositiveTest() {
        prepare();
        Mockito.when(service.findAllById(list)).thenReturn(listDto);
        ResponseEntity<List<ProfileDto>> result = controller.readAllById(list);
        assertEquals(listDto, result.getBody());
    }

    @Test
    @DisplayName("Чтение по нескольким несуществующим id, негативный сценарий")
    void readAllByIdNotFoundExceptionNegativeTest() {
        prepare();
        Mockito.when(service.findAllById(anyList())).thenReturn(Collections.emptyList());
        ResponseEntity<List<ProfileDto>> result = controller.readAllById(list);
        assertTrue(result.getBody().isEmpty());
    }
}
