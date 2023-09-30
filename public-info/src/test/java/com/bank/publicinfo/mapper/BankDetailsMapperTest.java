package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetailsEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BankDetailsMapperTest {
    private BankDetailsEntity bankDetailsEntity1;
    private BankDetailsEntity bankDetailsEntity2;
    private BankDetailsDto bankDetailsDto;
    private BankDetailsMapper bankDetailsMapper;

    @BeforeEach
    void setUp() {
        bankDetailsMapper = new BankDetailsMapperImpl();
        bankDetailsEntity1 = new BankDetailsEntity(null, "041234567", 1234567890L, 123456789L,
                new BigDecimal("30145678901234567890"), "City 1", "ПАО", "Bank 1");
        bankDetailsEntity2 = new BankDetailsEntity(1L, "041234567", 1234567890L, 123456789L,
                new BigDecimal("30145678901234567890"), "City 1", "ПАО", "Bank 1");
        bankDetailsDto = new BankDetailsDto(1L, "041234567", 1234567890L, 123456789L,
                new BigDecimal("30145678901234567890"), "City 1", "ПАО", "Bank 1");
    }

    @Test
    @DisplayName("маппинг в entity")
    void toEntityTest() {
        BankDetailsEntity actual = bankDetailsMapper.toEntity(bankDetailsDto);
        assertThat(bankDetailsEntity1).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    @DisplayName("маппинг в энтити, на вход подан null")
    void toEntityIfNullTest() {
        assertThat(bankDetailsMapper.toEntity(null)).isNull();
    }

    @Test
    @DisplayName("маппинг в дто")
    void toDtoTest() {
        BankDetailsDto actual = bankDetailsMapper.toDto(bankDetailsEntity2);
        assertThat(bankDetailsDto).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    @DisplayName("маппинг в дто, на вход подан null")
    void toDtoIfNullTest() {
        assertThat(bankDetailsMapper.toDto(null)).isNull();
    }

    @Test
    @DisplayName("слияние в entity")
    void mergeToEntityTest() {
        BankDetailsEntity actual = bankDetailsMapper.mergeToEntity(bankDetailsDto, bankDetailsEntity1);
        assertThat(bankDetailsEntity1).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null")
    void mergeToEntityIfNullTest() {
        assertThat(bankDetailsMapper.mergeToEntity(null, null)).isNull();
    }

    @Test
    @DisplayName("маппинг в дто лист")
    void toDtoListTest() {
        List<BankDetailsEntity> list = new ArrayList<>();
        list.add(bankDetailsEntity1);
        list.add(bankDetailsEntity2);
        List<BankDetailsDto> actual = bankDetailsMapper.toDtoList(list);
        assertThat(list.size()).isEqualTo(actual.size());
    }

    @Test
    @DisplayName("маппинг в дто лист, на вход подан null")
    void toDtoListIfNullTest() {
        assertThat(bankDetailsMapper.toDtoList(null)).isNull();
    }
}