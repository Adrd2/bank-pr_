package com.bank.history.mapper;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class HistoryMapperTest {

    @Spy
    private HistoryMapper mapper = new HistoryMapperImpl();
    private HistoryDto dto;
    private HistoryEntity entity, entity2;

    @BeforeEach
    void prepare() {
        entity = new HistoryEntity
                (10L, 20L, 30L, 40L,
                        50L, 60L, 70L);
        entity2 = new HistoryEntity
                (10L, 20L, 30L, 40L,
                        50L, 60L, 70L);
        dto = new HistoryDto
                (10L, 20L, 30L, 40L,
                        50L, 60L, 70L);
    }

    @Test
    @DisplayName("маппинг в entity")
    void toEntityTest() {
        HistoryEntity result = mapper.toEntity(dto);
        assertAll(
                () -> {
                    assertNull(result.getId());
                    assertEquals(dto.getProfileAuditId(), result.getProfileAuditId());
                    assertEquals(dto.getAccountAuditId(), result.getAccountAuditId());
                    assertEquals(dto.getTransferAuditId(), result.getTransferAuditId());
                    assertEquals(dto.getAntiFraudAuditId(), result.getAntiFraudAuditId());
                    assertEquals(dto.getAuthorizationAuditId(), result.getAuthorizationAuditId());
                    assertEquals(dto.getPublicBankInfoAuditId(), result.getPublicBankInfoAuditId());
                }
        );
    }

    @Test
    @DisplayName("маппинг в Entity, на вход подан null")
    void toEntityNullTest() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("Слияние в entity")
    void mergeToEntity() {
        HistoryEntity actual = mapper.mergeToEntity(dto, entity);
        assertAll(
                () -> {
                    assertEquals(dto.getId(), actual.getId());
                    assertEquals(dto.getProfileAuditId(), actual.getProfileAuditId());
                    assertEquals(dto.getAccountAuditId(), actual.getAccountAuditId());
                    assertEquals(dto.getTransferAuditId(), actual.getTransferAuditId());
                    assertEquals(dto.getAntiFraudAuditId(), actual.getAntiFraudAuditId());
                    assertEquals(dto.getAuthorizationAuditId(), actual.getAuthorizationAuditId());
                    assertEquals(dto.getPublicBankInfoAuditId(), actual.getPublicBankInfoAuditId());
                }
        );
    }

    @Test
    @DisplayName("слияние в Entity, на вход поданы null")
    void mergeToEntityNullTest() {
        assertNull(mapper.mergeToEntity(null, null));
    }

    @Test
    @DisplayName("маппинг в dto")
    void toDtoTest() {
        HistoryDto actual = mapper.toDto(entity);
        assertAll(
                () -> {
                    assertEquals(entity.getId(), actual.getId());
                    assertEquals(entity.getProfileAuditId(), actual.getProfileAuditId());
                    assertEquals(entity.getAccountAuditId(), actual.getAccountAuditId());
                    assertEquals(entity.getTransferAuditId(), actual.getTransferAuditId());
                    assertEquals(entity.getAntiFraudAuditId(), actual.getAntiFraudAuditId());
                    assertEquals(entity.getAuthorizationAuditId(), actual.getAuthorizationAuditId());
                    assertEquals(entity.getPublicBankInfoAuditId(), actual.getPublicBankInfoAuditId());
                }
        );
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан null")
    void toDtoNullTest() {
        assertNull(mapper.toDto(null));
    }

    @Test
    @DisplayName("маппинг в dto лист")
    void toListDtoTest() {
        List<HistoryEntity> list = new ArrayList<>();
        list.add(entity);
        list.add(entity2);
        List<HistoryDto> actual = mapper.toListDto(list);
        assertThat(list.size()).isEqualTo(actual.size());
    }

    @Test
    @DisplayName("маппинг в dto лист, на вход подан null")
    void toListDtoIfNullTest() {
        assertThat(mapper.toListDto(null)).isNull();
    }
}