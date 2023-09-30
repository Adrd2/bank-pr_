package com.bank.profile.modal.mapper;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.RegistrationMapper;
import com.bank.profile.mapper.RegistrationMapperImpl;
import com.bank.profile.repository.RegistrationRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doReturn;

public class RegistrationMapperTest {
    RegistrationEntity entity;
    RegistrationEntity anotherEntity;
    RegistrationDto dto;
    RegistrationRepository repository;
    RegistrationMapper mapper;
    RegistrationMapper mapper2;

    @BeforeEach
    void prepare() {
        repository = mock(RegistrationRepository.class);
        mapper = new RegistrationMapperImpl();
        entity = new RegistrationEntity(null, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        anotherEntity = new RegistrationEntity(2L, "Another_country", "Another_region",
                "Another_city", "Another_district", "Another_locality",
                "Another_street", "Another_house_number", "Another_house_block",
                "Another_flat_number", 2L
        );
        dto = mapper.toDto(entity);
        mapper2 = mock(RegistrationMapper.class);
    }

    @Test
    @DisplayName("Маппинг в entity")
    void toEntityTest() {
        prepare();
        RegistrationEntity result = mapper.toEntity(dto);

        assertAll(
                () -> {
                    assertEquals(result.getId(), dto.getId());
                    assertEquals(result.getHouseNumber(), dto.getHouseNumber());
                    assertEquals(result.getIndex(), dto.getIndex());
                    assertEquals(result.getCountry(), dto.getCountry());
                    assertEquals(result.getCity(), dto.getCity());
                    assertEquals(result.getDistrict(), result.getDistrict());
                    assertEquals(result.getStreet(), dto.getStreet());
                    assertEquals(result.getFlatNumber(), dto.getFlatNumber());
                    assertEquals(result.getHouseBlock(), dto.getHouseBlock());
                    assertEquals(result.getLocality(), dto.getLocality());
                    assertEquals(result.getRegion(), dto.getRegion());

                }
        );
    }

    @Test
    @DisplayName("Маппинг в entity, на вход подается null")
    void toEntityNullTest() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("Маппинг в dto")
    void toDtoTest() {
        prepare();
        RegistrationDto result = mapper.toDto(entity);

        assertAll(
                () -> {
                    assertEquals(result.getId(), dto.getId());
                    assertEquals(result.getHouseNumber(), dto.getHouseNumber());
                    assertEquals(result.getIndex(), dto.getIndex());
                    assertEquals(result.getCountry(), dto.getCountry());
                    assertEquals(result.getCity(), dto.getCity());
                    assertEquals(result.getDistrict(), result.getDistrict());
                    assertEquals(result.getStreet(), dto.getStreet());
                    assertEquals(result.getFlatNumber(), dto.getFlatNumber());
                    assertEquals(result.getHouseBlock(), dto.getHouseBlock());
                    assertEquals(result.getLocality(), dto.getLocality());
                    assertEquals(result.getRegion(), dto.getRegion());
                }
        );
    }

    @Test
    @DisplayName("Маппинг в dto, на вход подается null")
    void toDtoNullTest() {
        assertNull(mapper.toDto(null));
    }

    @Test
    @DisplayName("Слияние в entity")
    void mergeToEntityTest() {
        prepare();
        RegistrationEntity result = mapper.mergeToEntity(dto, entity);

        assertAll(
                () -> {
                    assertEquals(result.getId(), dto.getId());
                    assertEquals(result.getHouseNumber(), dto.getHouseNumber());
                    assertEquals(result.getIndex(), dto.getIndex());
                    assertEquals(result.getCountry(), dto.getCountry());
                    assertEquals(result.getCity(), dto.getCity());
                    assertEquals(result.getDistrict(), result.getDistrict());
                    assertEquals(result.getStreet(), dto.getStreet());
                    assertEquals(result.getFlatNumber(), dto.getFlatNumber());
                    assertEquals(result.getHouseBlock(), dto.getHouseBlock());
                    assertEquals(result.getLocality(), dto.getLocality());
                    assertEquals(result.getRegion(), dto.getRegion());
                }
        );
    }

    @Test
    @DisplayName("Слияние в entity, на вход подается null")
    void mergeToEntityNullTest() {
        assertNull(mapper.mergeToEntity(null, null));
    }

    @Test
    @DisplayName("Маппинг в список dto")
    void listToDtoTest() {
        prepare();
        doReturn(List.of(entity)).when(mapper2).toDtoList(List.of(entity));
        List<RegistrationDto> result = mapper.toDtoList(List.of(entity));

        assertAll(
                () -> {
                    assertEquals(result.get(0).getId(), entity.getId());
                    assertEquals(result.get(0).getCity(), entity.getCity());
                    assertEquals(result.get(0).getDistrict(), entity.getDistrict());
                    assertEquals(result.get(0).getIndex(), entity.getIndex());
                    assertEquals(result.get(0).getLocality(), entity.getLocality());
                    assertEquals(result.get(0).getStreet(), entity.getStreet());
                    assertEquals(result.get(0).getRegion(), entity.getRegion());
                    assertEquals(result.get(0).getHouseNumber(), entity.getHouseNumber());
                    assertEquals(result.get(0).getCountry(), entity.getCountry());
                    assertEquals(result.get(0).getHouseBlock(), entity.getHouseBlock());
                    assertEquals(result.get(0).getFlatNumber(), entity.getFlatNumber());
                }
        );
    }

    @Test
    @DisplayName("Маппинг в список dto, негативный сценарий")
    void listToDtoNegativeTest() {
        prepare();
        doReturn(List.of(anotherEntity)).when(mapper2).toDtoList(List.of(entity));
        List<RegistrationDto> result = mapper.toDtoList(List.of(entity));
        assertAll(
                () -> {
                    assertNotEquals(result.get(0).getId(), anotherEntity.getId());
                    assertNotEquals(result.get(0).getCity(), anotherEntity.getCity());
                    assertNotEquals(result.get(0).getRegion(), anotherEntity.getRegion());
                    assertNotEquals(result.get(0).getFlatNumber(), anotherEntity.getFlatNumber());
                    assertNotEquals(result.get(0).getIndex(), anotherEntity.getIndex());
                    assertNotEquals(result.get(0).getStreet(), anotherEntity.getStreet());
                    assertNotEquals(result.get(0).getHouseNumber(), anotherEntity.getHouseNumber());
                    assertNotEquals(result.get(0).getLocality(), anotherEntity.getLocality());
                    assertNotEquals(result.get(0).getDistrict(), anotherEntity.getDistrict());
                    assertNotEquals(result.get(0).getHouseBlock(), anotherEntity.getHouseBlock());
                    assertNotEquals(result.get(0).getCountry(), anotherEntity.getCountry());
                }
        );
    }
}
