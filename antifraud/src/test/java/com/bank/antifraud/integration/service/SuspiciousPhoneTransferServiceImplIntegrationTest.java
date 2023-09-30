package com.bank.antifraud.integration.service;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.integration.TestContainer;
import com.bank.antifraud.service.SuspiciousPhoneTransferService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Sql(value = "/create-suspicious-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/delete-suspicious-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class SuspiciousPhoneTransferServiceImplIntegrationTest extends TestContainer {
    @Autowired
    SuspiciousPhoneTransferService service;
    private final SuspiciousPhoneTransferDto dto
            = new SuspiciousPhoneTransferDto(null, 1L, false
            , false, "blockedReason1", "suspicionReason1");

    @Test
    @DisplayName("Сохранение, позитивный сценарий")
    void savePositiveTest() {
        SuspiciousPhoneTransferDto save = service.save(dto);

        assertAll(() -> {
            assertEquals(dto.getBlockedReason(), save.getBlockedReason());
            assertNotEquals(dto.getId(), save.getId());
        });
    }

    @Test
    @DisplayName("Сохранение, негативный сценарий")
    void saveNegativeTest() {
        assertThrows(DataIntegrityViolationException.class, () -> service.save(new SuspiciousPhoneTransferDto()));
    }

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        SuspiciousPhoneTransferDto byId = service.findById(4L);

        assertAll(() -> {
            assertEquals("blockedReason4", byId.getBlockedReason());
            assertNotEquals(2L, byId.getId());
            assertDoesNotThrow(() -> service.findById(4L));
        });
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    void findByIdNegativeTest() {
        EntityNotFoundException entityNotFoundException
                = assertThrows(EntityNotFoundException.class, () -> service.findById(1L));
        assertEquals("SuspiciousPhoneTransfer по данному id не существует"
                , entityNotFoundException.getMessage());

    }

    @Test
    @DisplayName("Обновление, позитивный сценарий")
    void updatePositiveTest() {
        SuspiciousPhoneTransferDto update = service.update(4L, dto);
        assertEquals(4L, update.getId());
        assertDoesNotThrow(() -> service.update(4L, dto));
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    void updateNegativeTest() {
        EntityNotFoundException entityNotFoundException
                = assertThrows(EntityNotFoundException.class, () -> service.update(1L, dto));
        assertEquals("SuspiciousPhoneTransfer по данному id не существует"
                , entityNotFoundException.getMessage());
    }

    @Test
    @DisplayName("Поиск всех сущностей, позитивный сценарий")
    void findAllByIdPositiveTest() {
        List<SuspiciousPhoneTransferDto> allById = service.findAllById(Arrays.asList(4L, 2L));
        assertEquals("blockedReason4", allById.get(0).getBlockedReason());
        assertEquals("blockedReason2", allById.get(1).getBlockedReason());
        assertDoesNotThrow(() -> service.findAllById(Arrays.asList(4L, 2L)));
    }

    @Test
    @DisplayName("Поиск всех сущностей, негативный сценарий")
    void findAllByIdNegativeTest() {
        EntityNotFoundException entityNotFoundException
                = assertThrows(EntityNotFoundException.class, () -> service.findAllById(Arrays.asList(1L, 5L)));
        assertEquals("SuspiciousPhoneTransfer по данному id не существует"
                , entityNotFoundException.getMessage());
    }
}
