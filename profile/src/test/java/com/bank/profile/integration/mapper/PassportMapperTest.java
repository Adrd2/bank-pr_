package com.bank.profile.integration.mapper;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.PassportMapper;
import com.bank.profile.mapper.PassportMapperImpl;
import com.bank.profile.repository.PassportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class PassportMapperTest {
    PassportEntity entity;
    PassportDto dto;
    PassportRepository repository;
    PassportMapper mapper;
    RegistrationEntity registration;
    RegistrationDto registrationDto;
    PassportMapper mapper2;
    PassportEntity anotherEntity;

    @BeforeEach
    void prepare() {
        registration = new RegistrationEntity(null, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);
        registrationDto = new RegistrationDto(null, "country", "region", "city",
                "district", "locality", "street", "house_number",
                "house_block", "flat_number", 1L);

        repository = Mockito.mock(PassportRepository.class);
        mapper = new PassportMapperImpl();

        entity = new PassportEntity(null, 1, 1L, "lastName", "firstName",
                "middleName", "gender", LocalDate.now(), "birthPlace",
                "issuedBy", LocalDate.now(), 1, LocalDate.now(), registration);
        dto = new PassportDto(null, 1, 2L, "lastname", "firstname",
                "middlename", "gender", LocalDate.now(), "birthPlace",
                "issuedBy", LocalDate.now(), 123, LocalDate.now(), registrationDto);
        anotherEntity = new PassportEntity(null, 1, 1L, "lastName", "firstName",
                "middleName", "gender", LocalDate.now(), "birthPlace",
                "issuedBy", LocalDate.now(), 1, LocalDate.now(), registration);
        mapper2 = mock(PassportMapper.class);
    }

    @Test
    @DisplayName("Маппинг в entity")
    void toEntityTest() {
        prepare();
        PassportEntity result = mapper.toEntity(dto);

        assertAll(
                () -> {
                    assertEquals(dto.getNumber(), result.getNumber());
                    assertEquals(dto.getId(), result.getId());
                    assertEquals(dto.getDivisionCode(), result.getDivisionCode());
                    assertEquals(dto.getSeries(), result.getSeries());
                    assertEquals(dto.getGender(), result.getGender());
                    assertEquals(dto.getBirthDate(), result.getBirthDate());
                    assertEquals(dto.getBirthPlace(), result.getBirthPlace());
                    assertEquals(dto.getDateOfIssue(), result.getDateOfIssue());
                    assertEquals(dto.getExpirationDate(), result.getExpirationDate());
                    assertEquals(dto.getFirstName(), result.getFirstName());
                    assertEquals(dto.getIssuedBy(), result.getIssuedBy());
                    assertEquals(dto.getLastName(), result.getLastName());
                    assertEquals(dto.getMiddleName(), result.getMiddleName());

                    assertEquals(result.getRegistration().getIndex(), entity.getRegistration().getIndex());
                    assertEquals(result.getRegistration().getStreet(), entity.getRegistration().getStreet());
                    assertEquals(result.getRegistration().getRegion(), entity.getRegistration().getRegion());
                    assertEquals(result.getRegistration().getCity(), entity.getRegistration().getCity());
                    assertEquals(result.getRegistration().getHouseNumber(), entity.getRegistration().getHouseNumber());
                    assertEquals(result.getRegistration().getHouseBlock(), entity.getRegistration().getHouseBlock());
                    assertEquals(result.getRegistration().getFlatNumber(), entity.getRegistration().getFlatNumber());
                    assertEquals(result.getRegistration().getFlatNumber(), entity.getRegistration().getFlatNumber());
                    assertEquals(result.getRegistration().getLocality(), entity.getRegistration().getLocality());
                    assertEquals(result.getRegistration().getCountry(), entity.getRegistration().getCountry());
                    assertEquals(result.getRegistration().getDistrict(), entity.getRegistration().getDistrict());
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
        PassportDto result = mapper.toDto(entity);

        assertAll(
                () -> {
                    assertEquals(entity.getNumber(), result.getNumber());
                    assertEquals(entity.getId(), result.getId());
                    assertEquals(entity.getDivisionCode(), result.getDivisionCode());
                    assertEquals(entity.getSeries(), result.getSeries());
                    assertEquals(entity.getGender(), result.getGender());
                    assertEquals(entity.getBirthDate(), result.getBirthDate());
                    assertEquals(entity.getBirthPlace(), result.getBirthPlace());
                    assertEquals(entity.getDateOfIssue(), result.getDateOfIssue());
                    assertEquals(entity.getExpirationDate(), result.getExpirationDate());
                    assertEquals(entity.getFirstName(), result.getFirstName());
                    assertEquals(entity.getIssuedBy(), result.getIssuedBy());
                    assertEquals(entity.getLastName(), result.getLastName());
                    assertEquals(entity.getMiddleName(), result.getMiddleName());

                    assertEquals(result.getRegistration().getIndex(), entity.getRegistration().getIndex());
                    assertEquals(result.getRegistration().getStreet(), entity.getRegistration().getStreet());
                    assertEquals(result.getRegistration().getRegion(), entity.getRegistration().getRegion());
                    assertEquals(result.getRegistration().getCity(), entity.getRegistration().getCity());
                    assertEquals(result.getRegistration().getHouseNumber(), entity.getRegistration().getHouseNumber());
                    assertEquals(result.getRegistration().getHouseBlock(), entity.getRegistration().getHouseBlock());
                    assertEquals(result.getRegistration().getFlatNumber(), entity.getRegistration().getFlatNumber());
                    assertEquals(result.getRegistration().getFlatNumber(), entity.getRegistration().getFlatNumber());
                    assertEquals(result.getRegistration().getLocality(), entity.getRegistration().getLocality());
                    assertEquals(result.getRegistration().getCountry(), entity.getRegistration().getCountry());
                    assertEquals(result.getRegistration().getDistrict(), entity.getRegistration().getDistrict());
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
        PassportEntity result = mapper.mergeToEntity(dto, entity);

        assertAll(
                () -> {
                    assertEquals(dto.getNumber(), result.getNumber());
                    assertEquals(dto.getId(), result.getId());
                    assertEquals(dto.getDivisionCode(), result.getDivisionCode());
                    assertEquals(dto.getSeries(), result.getSeries());
                    assertEquals(dto.getGender(), result.getGender());
                    assertEquals(dto.getBirthDate(), result.getBirthDate());
                    assertEquals(dto.getBirthPlace(), result.getBirthPlace());
                    assertEquals(dto.getDateOfIssue(), result.getDateOfIssue());
                    assertEquals(dto.getExpirationDate(), result.getExpirationDate());
                    assertEquals(dto.getFirstName(), result.getFirstName());
                    assertEquals(dto.getIssuedBy(), result.getIssuedBy());
                    assertEquals(dto.getLastName(), result.getLastName());
                    assertEquals(dto.getMiddleName(), result.getMiddleName());

                    assertEquals(result.getRegistration().getIndex(), entity.getRegistration().getIndex());
                    assertEquals(result.getRegistration().getStreet(), entity.getRegistration().getStreet());
                    assertEquals(result.getRegistration().getRegion(), entity.getRegistration().getRegion());
                    assertEquals(result.getRegistration().getCity(), entity.getRegistration().getCity());
                    assertEquals(result.getRegistration().getHouseNumber(), entity.getRegistration().getHouseNumber());
                    assertEquals(result.getRegistration().getHouseBlock(), entity.getRegistration().getHouseBlock());
                    assertEquals(result.getRegistration().getFlatNumber(), entity.getRegistration().getFlatNumber());
                    assertEquals(result.getRegistration().getFlatNumber(), entity.getRegistration().getFlatNumber());
                    assertEquals(result.getRegistration().getLocality(), entity.getRegistration().getLocality());
                    assertEquals(result.getRegistration().getCountry(), entity.getRegistration().getCountry());
                    assertEquals(result.getRegistration().getDistrict(), entity.getRegistration().getDistrict());
                }
        );
    }

    @Test
    @DisplayName("Слияние в entity, на вход подается null")
    void mergeToEntityNulTest() {
        assertNull(mapper.mergeToEntity(null, null));
    }

    @Test
    @DisplayName("Маппинг в список dto")
    void listToDtoTest() {
        prepare();
        doReturn(List.of(anotherEntity)).when(mapper2).toDtoList(List.of(anotherEntity));
        List<PassportDto> result = mapper.toDtoList(List.of(anotherEntity));

        assertAll(
                () -> {
                    assertEquals(result.get(0).getId(), anotherEntity.getId());
                    assertEquals(result.get(0).getNumber(), anotherEntity.getNumber());
                    assertEquals(result.get(0).getLastName(), anotherEntity.getLastName());
                    assertEquals(result.get(0).getGender(), anotherEntity.getGender());
                    assertEquals(result.get(0).getSeries(), anotherEntity.getSeries());
                    assertEquals(result.get(0).getMiddleName(), anotherEntity.getMiddleName());
                    assertEquals(result.get(0).getIssuedBy(), anotherEntity.getIssuedBy());
                    assertEquals(result.get(0).getFirstName(), anotherEntity.getFirstName());
                    assertEquals(result.get(0).getExpirationDate(), anotherEntity.getExpirationDate());
                    assertEquals(result.get(0).getDateOfIssue(), anotherEntity.getDateOfIssue());
                    assertEquals(result.get(0).getDateOfIssue(), anotherEntity.getDateOfIssue());
                    assertEquals(result.get(0).getBirthPlace(), anotherEntity.getBirthPlace());
                    assertEquals(result.get(0).getBirthDate(), anotherEntity.getBirthDate());
                }
        );
    }

    @Test
    @DisplayName("Маппинг в список dto, негативный сценарий")
    void listToDtoNegativeTest() {
        prepare();
        doReturn(List.of(anotherEntity)).when(mapper2).toDtoList(List.of(entity));
        List<PassportDto> result = mapper.toDtoList(List.of(entity));
        assertAll(
                () -> {
                    assertEquals(result.get(0).getId(), anotherEntity.getId());
                    assertEquals(result.get(0).getNumber(), anotherEntity.getNumber());
                    assertEquals(result.get(0).getLastName(), anotherEntity.getLastName());
                    assertEquals(result.get(0).getGender(), anotherEntity.getGender());
                    assertEquals(result.get(0).getSeries(), anotherEntity.getSeries());
                    assertEquals(result.get(0).getMiddleName(), anotherEntity.getMiddleName());
                    assertEquals(result.get(0).getIssuedBy(), anotherEntity.getIssuedBy());
                    assertEquals(result.get(0).getFirstName(), anotherEntity.getFirstName());
                    assertEquals(result.get(0).getExpirationDate(), anotherEntity.getExpirationDate());
                    assertEquals(result.get(0).getDateOfIssue(), anotherEntity.getDateOfIssue());
                    assertEquals(result.get(0).getDateOfIssue(), anotherEntity.getDateOfIssue());
                    assertEquals(result.get(0).getBirthPlace(), anotherEntity.getBirthPlace());
                    assertEquals(result.get(0).getBirthDate(), anotherEntity.getBirthDate());
                }
        );
    }
}
