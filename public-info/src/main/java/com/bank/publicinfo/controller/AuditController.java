package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.entity.AuditEntity;
import com.bank.publicinfo.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для {@link AuditEntity}
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Контроллер для аудита")
@RequestMapping("/audit")
public class AuditController {

    private final AuditService service;

    /**
     * @param id технический идентификатор {@link AuditEntity}
     * @return {@link ResponseEntity<AuditDto>}
     */
    @Operation(summary = "Получение информации об аудите по id")
    @GetMapping("/{id}")
    public AuditDto read(@PathVariable("id") Long id) {
        return service.findById(id);
    }
}