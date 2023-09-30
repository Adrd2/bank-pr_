package com.bank.profile.modal.mapper;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.mapper.ActualRegistrationMapper;
import com.bank.profile.mapper.ActualRegistrationMapperImpl;
import com.bank.profile.repository.ActualRegistrationRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.mockito.Mockito;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ActualRegistrationMapperTest {
    ActualRegistrationEntity entity;
    ActualRegistrationDto dto;
    ActualRegistrationRepository repository;
    ActualRegistrationMapper mapper;
    ActualRegistrationMapper mapper2;
    ActualRegistrationEntity anotherEntity;

    @BeforeEach
    void prepare() {
        repository = Mockito.mock(ActualRegistrationRepository.class);
        mapper = new ActualRegistrationMapperImpl();

        entity = new ActualRegistrationEntity(null, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        dto = new ActualRegistrationDto(null, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        anotherEntity = new ActualRegistrationEntity(null, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        mapper2 = mock(ActualRegistrationMapper.class);
    }

    @Test
    @DisplayName("Маппинг в entity")
    void toEntityTest() {
        prepare();
        ActualRegistrationEntity result = mapper.toEntity(dto);

        assertAll(
                () -> {
                    assertEquals(dto.getId(), result.getId());
                    assertEquals(dto.getIndex(), result.getIndex());
                    assertEquals(dto.getCity(), result.getCity());
                    assertEquals(dto.getStreet(), result.getStreet());
                    assertEquals(dto.getRegion(), result.getRegion());
                    assertEquals(dto.getDistrict(), result.getDistrict());
                    assertEquals(dto.getCountry(), result.getCountry());
                    assertEquals(dto.getFlatNumber(), result.getFlatNumber());
                    assertEquals(dto.getHouseBlock(), result.getHouseBlock());
                    assertEquals(dto.getHouseNumber(), result.getHouseNumber());
                    assertEquals(dto.getLocality(), result.getLocality());
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
        ActualRegistrationDto result = mapper.toDto(entity);

        assertAll(
                () -> {
                    assertEquals(dto.getId(), result.getId());
                    assertEquals(dto.getIndex(), result.getIndex());
                    assertEquals(dto.getCity(), result.getCity());
                    assertEquals(dto.getStreet(), result.getStreet());
                    assertEquals(dto.getRegion(), result.getRegion());
                    assertEquals(dto.getDistrict(), result.getDistrict());
                    assertEquals(dto.getCountry(), result.getCountry());
                    assertEquals(dto.getFlatNumber(), result.getFlatNumber());
                    assertEquals(dto.getHouseBlock(), result.getHouseBlock());
                    assertEquals(dto.getHouseNumber(), result.getHouseNumber());
                    assertEquals(dto.getLocality(), result.getLocality());
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
        ActualRegistrationEntity result = mapper.mergeToEntity(dto, entity);

        assertAll(
                () -> {
                    assertEquals(dto.getId(), result.getId());
                    assertEquals(dto.getIndex(), result.getIndex());
                    assertEquals(dto.getCity(), result.getCity());
                    assertEquals(dto.getStreet(), result.getStreet());
                    assertEquals(dto.getRegion(), result.getRegion());
                    assertEquals(dto.getDistrict(), result.getDistrict());
                    assertEquals(dto.getCountry(), result.getCountry());
                    assertEquals(dto.getFlatNumber(), result.getFlatNumber());
                    assertEquals(dto.getHouseBlock(), result.getHouseBlock());
                    assertEquals(dto.getHouseNumber(), result.getHouseNumber());
                    assertEquals(dto.getLocality(), result.getLocality());
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
        doReturn(List.of(anotherEntity)).when(mapper2).toDtoList(List.of(anotherEntity));
        List<ActualRegistrationDto> result = mapper.toDtoList(List.of(anotherEntity));

        assertAll(
                () -> {
                    assertEquals(result.get(0).getId(), anotherEntity.getId());
                    assertEquals(result.get(0).getCity(), anotherEntity.getCity());
                    assertEquals(result.get(0).getLocality(), anotherEntity.getLocality());
                    assertEquals(result.get(0).getCountry(), anotherEntity.getCountry());
                    assertEquals(result.get(0).getDistrict(), anotherEntity.getDistrict());
                    assertEquals(result.get(0).getStreet(), anotherEntity.getStreet());
                    assertEquals(result.get(0).getRegion(), anotherEntity.getRegion());
                    assertEquals(result.get(0).getHouseNumber(), anotherEntity.getHouseNumber());
                    assertEquals(result.get(0).getFlatNumber(), anotherEntity.getFlatNumber());
                    assertEquals(result.get(0).getHouseBlock(), anotherEntity.getHouseBlock());
                }
        );
    }

    @Test
    @DisplayName("Маппинг в список dto, негативный сценарий")
    void listToDtoNegativeTest() {
        prepare();
        doReturn(List.of(anotherEntity)).when(mapper2).toDtoList(List.of(entity));
        List<ActualRegistrationDto> result = mapper.toDtoList(List.of(entity));
        assertAll(
                () -> {
                    assertEquals(result.get(0).getId(), anotherEntity.getId());
                    assertEquals(result.get(0).getCity(), anotherEntity.getCity());
                    assertEquals(result.get(0).getLocality(), anotherEntity.getLocality());
                    assertEquals(result.get(0).getCountry(), anotherEntity.getCountry());
                    assertEquals(result.get(0).getDistrict(), anotherEntity.getDistrict());
                    assertEquals(result.get(0).getStreet(), anotherEntity.getStreet());
                    assertEquals(result.get(0).getRegion(), anotherEntity.getRegion());
                    assertEquals(result.get(0).getHouseNumber(), anotherEntity.getHouseNumber());
                    assertEquals(result.get(0).getFlatNumber(), anotherEntity.getFlatNumber());
                    assertEquals(result.get(0).getHouseBlock(), anotherEntity.getHouseBlock());
                }
        );
    }
}