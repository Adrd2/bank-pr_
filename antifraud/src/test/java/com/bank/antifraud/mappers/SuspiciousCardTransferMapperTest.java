package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransferEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SuspiciousCardTransferMapperTest {
    @Spy
    SuspiciousCardTransferMapper mapper = new SuspiciousCardTransferMapperImpl();

    private static SuspiciousCardTransferEntity entity1;
    private static SuspiciousCardTransferEntity entity2;
    private static SuspiciousCardTransferDto dto;

    @BeforeAll
    static void init() {
        entity1 = new SuspiciousCardTransferEntity(1L, 1L, false
                , false, "blockedReason1", "suspicionReason1");
        entity2 = new SuspiciousCardTransferEntity(2L, 2L, true
                , true, "blockedReason2", "suspicionReason2");
        dto = new SuspiciousCardTransferDto(null, 3L, false
                , true, "dtoBlockedReason", "dtoSuspicionReason");
    }

    @Test
    @DisplayName("Маппинг в дто")
    void toDtoTest() {
        SuspiciousCardTransferDto dto = mapper.toDto(entity1);

        assertAll(() -> {
            assertEquals(entity1.getId(), dto.getId());
            assertEquals(entity1.getCardTransferId(), dto.getCardTransferId());
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
        SuspiciousCardTransferEntity entity = mapper.toEntity(dto);

        assertAll(() -> {
            assertNull(entity.getId());
            assertEquals(dto.getCardTransferId(), entity.getCardTransferId());
            assertEquals(dto.getIsBlocked(),entity.getIsBlocked());
            assertEquals(dto.getIsSuspicious(),entity.getIsSuspicious());
            assertEquals(dto.getBlockedReason(), entity.getBlockedReason());
            assertEquals(dto.getSuspiciousReason(),entity.getSuspiciousReason());
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
        List<SuspiciousCardTransferEntity> list = new ArrayList<>();
        list.add(entity1);
        list.add(entity2);

        List<SuspiciousCardTransferDto> listDto = mapper.toListDto(list);
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
        SuspiciousCardTransferEntity entity = mapper.mergeToEntity(dto, entity1);
        assertAll(() -> {
            assertEquals(entity1.getId(), entity.getId());
            assertEquals(dto.getCardTransferId(), entity.getCardTransferId());
            assertEquals(dto.getIsBlocked(),entity.getIsBlocked());
            assertEquals(dto.getIsSuspicious(),entity.getIsSuspicious());
            assertEquals(dto.getBlockedReason(), entity.getBlockedReason());
            assertEquals(dto.getSuspiciousReason(),entity.getSuspiciousReason());
        });
    }

    @Test
    @DisplayName("Слияние в энтити, на вход подан null")
    void mergeToEntityIfDtoIsNullTest() {
        SuspiciousCardTransferEntity entity = mapper.mergeToEntity(null, entity1);
        assertAll(() -> {
            assertEquals(entity1.getId(), entity.getId());
            assertEquals(entity1.getCardTransferId(), entity.getCardTransferId());
            assertEquals(entity1.getIsBlocked(),entity.getIsBlocked());
            assertEquals(entity1.getIsSuspicious(),entity.getIsSuspicious());
            assertEquals(entity1.getBlockedReason(), entity.getBlockedReason());
            assertEquals(entity1.getSuspiciousReason(),entity.getSuspiciousReason());
        });
    }
}