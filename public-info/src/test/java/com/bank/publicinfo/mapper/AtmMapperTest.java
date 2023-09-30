package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.AtmEntity;
import com.bank.publicinfo.entity.BranchEntity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

class AtmMapperTest {
    private AtmEntity atmEntity1;
    private AtmEntity atmEntity2;
    private AtmDto atmDto;
    private AtmMapper atmMapper;

    @BeforeEach
    void setUp() {
        atmMapper = new AtmMapperImpl();
        atmEntity1 = new AtmEntity(null, "address", LocalTime.MIN,
                LocalTime.MAX, true,
                new BranchEntity(1L, "address", 89997771111L,
                        "City", LocalTime.MIN, LocalTime.MAX));
        atmEntity2 = new AtmEntity(1L, "address", LocalTime.MIN,
                LocalTime.MAX, true,
                new BranchEntity(1L, "address", 89997771111L,
                        "City", LocalTime.MIN, LocalTime.MAX));
        atmDto = new AtmDto(1L, "address",
                LocalTime.MIN, LocalTime.MAX, true,
                new BranchDto(1L, "address", 89997771111L,
                        "City", LocalTime.MIN, LocalTime.MAX));
    }

    @Test
    @DisplayName("маппинг в entity")
    void toEntityTest() {
        AtmEntity actual = atmMapper.toEntity(atmDto);
        assertThat(atmEntity1).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    @DisplayName("маппинг в энтити, на вход подан null")
    void toEntityIfNullTest() {
        assertThat(atmMapper.toEntity(null)).isNull();
    }

    @Test
    @DisplayName("маппинг в дто")
    void toDtoTest() {
        AtmDto actual = atmMapper.toDto(atmEntity2);
        assertThat(atmDto).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    @DisplayName("маппинг в дто, на вход подан null")
    void toDtoIfNullTest() {
        assertThat(atmMapper.toDto(null)).isNull();
    }

    @Test
    @DisplayName("слияние в entity")
    void mergeToEntityTest() {
        AtmEntity actual = atmMapper.mergeToEntity(atmDto, atmEntity1);
        assertThat(atmEntity1).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null")
    void mergeToEntityIfNullTest() {
        assertThat(atmMapper.mergeToEntity(null, null)).isNull();
    }

    @Test
    @DisplayName("маппинг в дто лист")
    void toDtoListTest() {
        List<AtmEntity> list = new ArrayList<>();
        list.add(atmEntity1);
        list.add(atmEntity2);
        List<AtmDto> actual = atmMapper.toDtoList(list);
        assertThat(list.size()).isEqualTo(actual.size());
    }

    @Test
    @DisplayName("маппинг в дто лист, на вход подан null")
    void toDtoListIfNullTest() {
        assertThat(atmMapper.toDtoList(null)).isNull();
    }
}