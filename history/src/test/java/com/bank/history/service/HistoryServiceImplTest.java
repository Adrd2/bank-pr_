package com.bank.history.service;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import com.bank.history.mapper.HistoryMapper;
import com.bank.history.mapper.HistoryMapperImpl;
import com.bank.history.repository.HistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(initializers = ConfigDataApplicationContextInitializer.class, classes = {
        HistoryServiceImpl.class, HistoryMapperImpl.class
})
class HistoryServiceImplTest {

    @MockBean
    HistoryRepository repository;
    @Autowired
    HistoryService service;
    @Autowired
    HistoryMapper mapper;
    HistoryEntity entity, entity1, entity2;
    HistoryDto dto;
    List<HistoryEntity> entityList;

    @BeforeEach
    void prepare() {
        entity = new HistoryEntity(1L, 2L, 3L, 4L,
                5L, 6L, 7L);
        dto = mapper.toDto(entity);

        entity1 = new HistoryEntity(10L, 20L, 30L, 40L,
                50L, 60L, 70L);
        entity2 = new HistoryEntity(100L, 200L, 300L, 400L,
                500L, 600L, 700L);
        dto = mapper.toDto(entity);
    }

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void readByIdPositiveTest() {
        doReturn(Optional.of(entity)).when(repository).findById(entity.getId());
        HistoryDto actual = service.readById(entity.getId());

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
    @DisplayName("поиск по несуществующему id, негативный сценарий")
    void findByNonExistIdNegativeTest() {
        when(repository.findById(123L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.readById(123L));
    }

    @Test
    @DisplayName("поиск по нескольким id, позитивный сценарий")
    void readAllByIdPositiveTest() {
        entityList = new ArrayList<>();
        ArrayList<Long> idList = new ArrayList<>(Arrays.asList(1L, 2L));
        entityList.add(entity);
        entityList.add(entity2);
        doReturn(entityList).when(repository).findAllById(idList);
        assertThat(entityList.size()).isEqualTo(service.readAllById(idList).size());
    }

    @Test
    @DisplayName("поиск по нескольким несуществующим id, негативный сценарий")
    void findAllByNonExistIdNegativeTest() {
        Long id = 123L;
        when(repository.findById(id)).thenReturn(Optional.empty());
        when(repository.findAllById(List.of(123L))).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> service.readById(id));
    }

    @Test
    @DisplayName("сохранение, позитивный сценарий")
    void savePositiveTest() {
        doReturn(entity).when(repository).save(any());
        HistoryDto actual = service.create(dto);

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
    @DisplayName("сохранение null dto, негативный сценарий")
    void saveNullDtoNegativeTest() {
        doReturn(Optional.of(entity)).when(repository).save(isNull());
        HistoryDto actual = service.create(dto);
        assertNull(actual);
    }

    @Test
    @DisplayName("обновление, позитивный сценарий")
    void updatePositiveTest() {
        doReturn(entity).when(repository).save(any());
        doReturn(Optional.of(entity)).when(repository).findById(entity.getId());
        HistoryDto actual = service.update(dto.getId(), dto);
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
    @DisplayName("обновление по несуществующему id, негативный сценарий")
    void updateNonExistIdNegativeTest() {
        Long id = 123L;
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.readById(id));
    }
}