package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.BranchEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BranchMapperTest {
    BranchEntity branchEntity1;
    BranchEntity branchEntity2;
    BranchDto branchDto;
    BranchMapper branchMapper;

    @BeforeEach
    void setUp() {
        branchMapper = new BranchMapperImpl();
        branchEntity1 = new BranchEntity(null, "address", 89998881111L,
                "City", LocalTime.MIN, LocalTime.MAX);
        branchEntity2 = new BranchEntity(1L, "address", 89998881111L,
                "City", LocalTime.MIN, LocalTime.MAX);
        branchDto = new BranchDto(1L, "address", 89998881111L,
                "City", LocalTime.MIN, LocalTime.MAX);
    }


    @Test
    @DisplayName("маппинг в entity")
    void toEntityTest() {
        BranchEntity actual = branchMapper.toEntity(branchDto);
        assertThat(branchEntity1).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    @DisplayName("маппинг в энтити, на вход подан null")
    void toEntityIfNullTest() {
        assertThat(branchMapper.toEntity(null)).isNull();
    }

    @Test
    @DisplayName("маппинг в дто")
    void toDtoTest() {
        BranchDto actual = branchMapper.toDto(branchEntity2);
        assertThat(branchDto).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    @DisplayName("маппинг в дто, на вход подан null")
    void toDtoIfNullTest() {
        assertThat(branchMapper.toDto(null)).isNull();
    }

    @Test
    @DisplayName("слияние в entity")
    void mergeToEntityTest() {
        BranchEntity actual = branchMapper.mergeToEntity(branchDto, branchEntity1);
        assertThat(branchEntity1).usingRecursiveComparison().isEqualTo(actual);
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null")
    void mergeToEntityIfNullTest() {
        assertThat(branchMapper.mergeToEntity(null, null)).isNull();
    }

    @Test
    @DisplayName("маппинг в дто лист")
    void toDtoListTest() {
        List<BranchEntity> list = new ArrayList<>();
        list.add(branchEntity1);
        list.add(branchEntity2);
        List<BranchDto> actual = branchMapper.toDtoList(list);
        assertThat(list.size()).isEqualTo(actual.size());
    }

    @Test
    @DisplayName("маппинг в дто лист, на вход подан null")
    void toDtoListIfNullTest() {
        assertThat(branchMapper.toDtoList(null)).isNull();
    }
}