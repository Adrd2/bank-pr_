package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.entity.BankDetailsEntity;
import com.bank.publicinfo.entity.LicenseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LicenseMapperTest {
    LicenseEntity licenseEntity1;
    LicenseEntity licenseEntity2;
    LicenseDto licenseDto;
    LicenseMapper licenseMapper;

    @BeforeEach
    void setUp() {
        licenseMapper = new LicenseMapperImpl();
        licenseEntity1 = new LicenseEntity(null, new Byte[]{1, 2, 3},
                new BankDetailsEntity(1L, "041234567", 1L, 1L, BigDecimal.ONE, "City",
                        "Company", "Name"));
        licenseEntity2 = new LicenseEntity(1L, new Byte[]{1, 2, 3},
                new BankDetailsEntity(1L, "041234567", 1L, 1L, BigDecimal.ONE, "City",
                        "Company", "Name"));
        licenseDto = new LicenseDto(1L, new Byte[]{1, 2, 3},
                new BankDetailsDto(1L, "041234567", 1L, 1L, BigDecimal.ONE, "City",
                        "Company", "Name"));
    }

    @Test
    @DisplayName("маппинг в entity")
    void toEntityTest() {
        LicenseEntity actual = licenseMapper.toEntity(licenseDto);
        assertThat(licenseEntity1).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    @DisplayName("маппинг в энтити, на вход подан null")
    void toEntityIfNullTest() {
        assertThat(licenseMapper.toEntity(null)).isNull();
    }

    @Test
    @DisplayName("маппинг в дто")
    void toDtoTest() {
        LicenseDto actual = licenseMapper.toDto(licenseEntity2);
        assertThat(licenseDto).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    @DisplayName("маппинг в дто, на вход подан null")
    void toDtoIfNullTest() {
        assertThat(licenseMapper.toDto(null)).isNull();
    }

    @Test
    @DisplayName("слияние в entity")
    void mergeToEntityTest() {
        LicenseEntity actual = licenseMapper.mergeToEntity(licenseDto, licenseEntity1);
        assertThat(licenseEntity1).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null")
    void mergeToEntityIfNullTest() {
        assertThat(licenseMapper.mergeToEntity(null, null)).isNull();
    }

    @Test
    @DisplayName("маппинг в дто лист")
    void toDtoListTest() {
        List<LicenseEntity> list = new ArrayList<>();
        list.add(licenseEntity1);
        list.add(licenseEntity2);
        List<LicenseDto> actual = licenseMapper.toDtoList(list);
        assertThat(list.size()).isEqualTo(actual.size());
    }

    @Test
    @DisplayName("маппинг в дто лист, на вход подан null")
    void toDtoListIfNullTest() {
        assertThat(licenseMapper.toDtoList(null)).isNull();
    }
}