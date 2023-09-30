package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.BankDetailsEntity;
import com.bank.publicinfo.entity.CertificateEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CertificateMapperTest {

    CertificateEntity certificateEntity1;
    CertificateEntity certificateEntity2;
    CertificateDto certificateDto;
    CertificateMapper certificateMapper;

    @BeforeEach
    void setUp() {
        certificateMapper = new CertificateMapperImpl();
        certificateEntity1 = new CertificateEntity(null, new Byte[]{1, 2, 3},
                new BankDetailsEntity(1L, "041234567", 1L, 1L, BigDecimal.ONE,
                        "City", "Company", "name"));
        certificateEntity2 = new CertificateEntity(1L, new Byte[]{1, 2, 3},
                new BankDetailsEntity(1L, "041234567", 1L, 1L, BigDecimal.ONE,
                        "City", "Company", "name"));
        certificateDto = new CertificateDto(1L, new Byte[]{1, 2, 3},
                new BankDetailsDto(1L, "041234567", 1L, 1L, BigDecimal.ONE,
                        "City", "Company", "name"));

    }

    @Test
    @DisplayName("маппинг в entity")
    void toEntityTest() {
        CertificateEntity actual = certificateMapper.toEntity(certificateDto);
        assertThat(certificateEntity1).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    @DisplayName("маппинг в энтити, на вход подан null")
    void toEntityIfNullTest() {
        assertThat(certificateMapper.toEntity(null)).isNull();
    }

    @Test
    @DisplayName("маппинг в дто")
    void toDtoTest() {
        CertificateDto actual = certificateMapper.toDto(certificateEntity2);
        assertThat(certificateDto).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    @DisplayName("маппинг в дто, на вход подан null")
    void toDtoIfNullTest() {
        assertThat(certificateMapper.toDto(null)).isNull();
    }

    @Test
    @DisplayName("слияние в entity")
    void mergeToEntityTest() {
        CertificateEntity actual = certificateMapper.mergeToEntity(certificateDto, certificateEntity1);
        assertThat(certificateEntity1).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null")
    void mergeToEntityIfNullTest() {
        assertThat(certificateMapper.mergeToEntity(null, null)).isNull();
    }

    @Test
    @DisplayName("маппинг в дто лист")
    void toDtoListTest() {
        List<CertificateEntity> list = new ArrayList<>();
        list.add(certificateEntity1);
        list.add(certificateEntity2);
        List<CertificateDto> actual = certificateMapper.toDtoList(list);
        assertThat(list.size()).isEqualTo(actual.size());
    }

    @Test
    @DisplayName("маппинг в дто лист, на вход подан null")
    void toDtoListIfNullTest() {
        assertThat(certificateMapper.toDtoList(null)).isNull();
    }
}