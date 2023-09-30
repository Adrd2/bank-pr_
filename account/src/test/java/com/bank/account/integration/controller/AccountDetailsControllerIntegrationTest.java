package com.bank.account.integration.controller;

import com.bank.account.AccountApplication;
import com.bank.account.controller.AccountDetailsController;
import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.integration.TestContainerConfig;
import com.bank.account.repository.AccountDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes =
                {AccountApplication.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Testcontainers
class AccountDetailsControllerIntegrationTest extends TestContainerConfig {
    @Autowired
    AccountDetailsController controller;
    @Autowired
    AccountDetailsRepository repository;
    AccountDetailsEntity entity;
    AccountDetailsEntity entity2;
    AccountDetailsDto dto;
    AccountDetailsDto dto2;

    private static List<Long> list;

    @BeforeEach
    void prepare() {
        MockitoAnnotations.openMocks(this);
        entity = new AccountDetailsEntity(1L, 85L, 10L,
                2L, BigDecimal.valueOf(100L, 2), false, 4L);
        dto = new AccountDetailsDto(1L, 85L, 10L,
                2L, BigDecimal.valueOf(100L, 2), false, 4L);
        entity2 = new AccountDetailsEntity(2L, 86L, 11L,
                3L, BigDecimal.valueOf(100L, 2), false, 4L);
        dto2 = new AccountDetailsDto(2L, 86L, 11L,
                3L, BigDecimal.valueOf(100L, 2), false, 4L);
        list = new ArrayList<>(List.of(1L));
        repository.save(entity);
    }

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() {
        Long id = 1L;
        ResponseEntity<?> result = controller.read(id, null);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeNest() {
        Long id = 123L;
        assertThrows(EntityNotFoundException.class, () -> controller.read(id, null));
    }

    @Test
    @DisplayName("создание, позитивный сценарий")
    void createPositiveTest() {
        ResponseEntity<?> result = controller.create(dto2, null);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("создание c null данными, негативный сценарий")
    void createNullDtoNegativeTest() {
        AccountDetailsDto dtoNull = new AccountDetailsDto();
        assertThrows(DataIntegrityViolationException.class,
                () -> controller.create(dtoNull, null));
    }

    @Test
    @DisplayName("обновление по id, позитивный сценарий")
    void updateByIdPositiveTest() {
        ResponseEntity<?> result = controller.update(dto.getId(), dto, null);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("обновление по несуществующему id, негативный сценарий")
    void updateByNonExistIdNegativeTest() {
        Long id = 123L;
        assertThrows(EntityNotFoundException.class, () -> controller.update(id, dto, null));
    }

    @Test
    @DisplayName("чтение по нескольким id, позитивный сценарий")
    void readAllPositiveTest() {
        ResponseEntity<?> result = controller.readAll(list, null);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    @DisplayName("чтение по нескольким несуществующим id, негативный сценарий")
    void readAllNonExistIdNegativeTest() {
        assertThrows(EntityNotFoundException.class, () -> controller.readAll(List.of(123L), null));
    }
}