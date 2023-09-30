package com.bank.antifraud.integration.mapper;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
import com.bank.antifraud.integration.TestContainer;
import com.bank.antifraud.mappers.SuspiciousPhoneTransferMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class SuspiciousPhoneTransferMapperIntegrationTest extends TestContainer {
    @Autowired
    SuspiciousPhoneTransferMapper mapper;
    private static SuspiciousPhoneTransferEntity entity1;
    private static SuspiciousPhoneTransferEntity entity2;
    private static SuspiciousPhoneTransferDto dto;

    @BeforeAll
    static void init() {
        entity1 = new SuspiciousPhoneTransferEntity(1L, 1L, false
                , false, "blockedReason1", "suspicionReason1");
        entity2 = new SuspiciousPhoneTransferEntity(2L, 2L, true
                , true, "blockedReason2", "suspicionReason2");
        dto = new SuspiciousPhoneTransferDto(null, 3L, false
                , true, "dtoBlockedReason", "dtoSuspicionReason");
    }

    @Test
    @DisplayName("Маппинг в дто")
    void toDtoTest() {
        SuspiciousPhoneTransferDto dto = mapper.toDto(entity1);

        assertAll(() -> {
            assertEquals(entity1.getId(), dto.getId());
            assertEquals(entity1.getPhoneTransferId(), dto.getPhoneTransferId());
            assertEquals(entity1.getIsBlocked(), dto.getIsBlocked());
            assertEquals(entity1.getIsSuspicious(), dto.getIsSuspicious());
            assertEquals(entity1.getBlockedReason(), dto.getBlockedReason());
            assertEquals(entity1.getSuspiciousReason(), dto.getSuspiciousReason());
        });
    }

    @Test
    @DisplayName("Маппинг в дто, null на вход")
    void toDtoNegativeIfNullTest() {
        assertNull(mapper.toDto(null));
    }

    @Test
    @DisplayName("Маппинг в энтити")
    void toEntityTest() {
        SuspiciousPhoneTransferEntity entity = mapper.toEntity(dto);

        assertAll(() -> {
            assertNull(entity.getId());
            assertEquals(dto.getPhoneTransferId(), entity.getPhoneTransferId());
            assertEquals(dto.getIsBlocked(), entity.getIsBlocked());
            assertEquals(dto.getIsSuspicious(), entity.getIsSuspicious());
            assertEquals(dto.getBlockedReason(), entity.getBlockedReason());
            assertEquals(dto.getSuspiciousReason(), entity.getSuspiciousReason());
        });
    }

    @Test
    @DisplayName("Маппинг в энтити, null на вход")
    void toEntityNegativeIfNullTest() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("Маппинг списка в дто")
    void toListDtoTest() {
        List<SuspiciousPhoneTransferEntity> list = new ArrayList<>();
        list.add(entity1);
        list.add(entity2);

        List<SuspiciousPhoneTransferDto> listDto = mapper.toListDto(list);
        assertEquals(list.size(), listDto.size());
    }

    @Test
    @DisplayName("Маппинг списка в дто, null на вход")
    void toDtoListNegativeIfNullTest() {
        assertNull(mapper.toListDto(null));
    }

    @Test
    @DisplayName("Слияние в энтити")
    void mergeToEntityTest() {
        SuspiciousPhoneTransferEntity entity = mapper.mergeToEntity(dto, entity1);
        assertAll(() -> {
            assertEquals(entity1.getId(), entity.getId());
            assertEquals(dto.getPhoneTransferId(), entity.getPhoneTransferId());
            assertEquals(dto.getIsBlocked(), entity.getIsBlocked());
            assertEquals(dto.getIsSuspicious(), entity.getIsSuspicious());
            assertEquals(dto.getBlockedReason(), entity.getBlockedReason());
            assertEquals(dto.getSuspiciousReason(), entity.getSuspiciousReason());
        });
    }

    @Test
    @DisplayName("Слияние в энтити, на вход подан null")
    void mergeToEntityIfDtoIsNullTest() {
        SuspiciousPhoneTransferEntity entity = mapper.mergeToEntity(null, entity1);
        assertAll(() -> {
            assertEquals(entity1.getId(), entity.getId());
            assertEquals(entity1.getPhoneTransferId(), entity.getPhoneTransferId());
            assertEquals(entity1.getIsBlocked(), entity.getIsBlocked());
            assertEquals(entity1.getIsSuspicious(), entity.getIsSuspicious());
            assertEquals(entity1.getBlockedReason(), entity.getBlockedReason());
            assertEquals(entity1.getSuspiciousReason(), entity.getSuspiciousReason());
        });
    }
}
