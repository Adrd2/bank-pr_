package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransferEntity;
import com.bank.antifraud.service.SuspiciousCardTransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер для {@link SuspiciousCardTransferDto}
 */

@RestController
@RequiredArgsConstructor
@Tag(name = "Контроллер для подозрительных переводов по банковской карте")
@RequestMapping("/suspicious/card/transfer")
public class SuspiciousCardTransferController {
    private final SuspiciousCardTransferService service;


    /**
     * @param id технический идентификатор {@link SuspiciousCardTransferEntity}
     * @return {@link ResponseEntity} {@link SuspiciousCardTransferDto}
     */
    @Operation(summary = "Получение информации о подозрительном переводе по банковской карте по id")
    @GetMapping("/{id}")
    public ResponseEntity<SuspiciousCardTransferDto> read(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * @param ids список технических идентификаторов {@link SuspiciousCardTransferEntity}
     * @return {@link ResponseEntity } c листом {@link SuspiciousCardTransferDto}
     */
    @Operation(summary = "Получение информации о нескольких подозрительных переводах по банковским картам по id")
    @GetMapping
    public ResponseEntity<List<SuspiciousCardTransferDto>> readAll(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.findAllById(ids));
    }

    /**
     * @param suspiciousTransfer {@link SuspiciousCardTransferDto}
     * @return {@link ResponseEntity} {@link SuspiciousCardTransferDto}
     */
    @Operation(summary = "Создание подозрительного перевода по банковской карте по id")
    @PostMapping("/create")
    public ResponseEntity<SuspiciousCardTransferDto> create(
            @Valid @RequestBody SuspiciousCardTransferDto suspiciousTransfer) {
        return ResponseEntity.ok(service.save(suspiciousTransfer));
    }

    /**
     * @param suspiciousTransfer {@link SuspiciousCardTransferDto}
     * @param id                 технический идентификатор {@link SuspiciousCardTransferEntity}
     * @return {@link ResponseEntity} {@link SuspiciousCardTransferDto}
     */
    @Operation(summary = "Обновление информации о подозрительном переводе по банковской карте по id")
    @PutMapping("/{id}")
    public ResponseEntity<SuspiciousCardTransferDto> update(
            @Valid @RequestBody SuspiciousCardTransferDto suspiciousTransfer,
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(service.update(id, suspiciousTransfer));
    }
}
