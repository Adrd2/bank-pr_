package com.bank.account.controller;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.feign.ProfileFeignClient;
import com.bank.account.service.AccountDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Контроллер для {@link AccountDetailsEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/details")
@Tag(name = "Контроллер для банковских счетов")
public class AccountDetailsController {

    private final AccountDetailsService service;
    private final ProfileFeignClient feignClient;


    /**
     * @param id технический идентификатор {@link AccountDetailsEntity}
     * @return {@link ResponseEntity<AccountDetailsDto>}
     */
    @GetMapping("/{id}")
    @Operation(summary = "Получение информации о банковском счете по id")
    public ResponseEntity<?> read(@PathVariable("id") Long id,
                                  @RequestParam(value = "profileId", required = false) Long profileId) {
        if (profileId != null) {
            final Map<Long, Object> result = new HashMap<>();
            result.put(1L, service.findById(id));
            result.put(2L, feignClient.read(profileId).getBody());
            return ResponseEntity.ok(result.values());
        }
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * @param accountDetails - сущность для создания в виде {@link AccountDetailsDto}
     * @return {@link ResponseEntity<AccountDetailsDto>}
     */
    @PostMapping("/create")
    @Operation(summary = "Создание нового банковского счета")
    public ResponseEntity<?> create(@RequestBody @Valid AccountDetailsDto accountDetails,
                                    @RequestParam(value = "profileId", required = false) Long profileId) {
        if (profileId != null) {
            final Map<Long, Object> result = new HashMap<>();
            result.put(1L, service.save(accountDetails));
            result.put(2L, feignClient.read(profileId).getBody());
            return ResponseEntity.ok(result.values());
        }
        return ResponseEntity.ok(service.save(accountDetails));
    }

    /**
     * @param accountDetails {@link AccountDetailsDto}
     * @param id             технический идентификатор {@link AccountDetailsEntity}
     * @return {@link ResponseEntity<AccountDetailsDto>}
     */
    @PutMapping("/update/{id}")
    @Operation(summary = "Обновление информации о банковском счете по id")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody @Valid AccountDetailsDto accountDetails,
                                    @RequestParam(value = "profileId", required = false) Long profileId) {
        if (profileId != null) {
            final Map<Long, Object> result = new HashMap<>();
            result.put(1L, service.update(id, accountDetails));
            result.put(2L, feignClient.update(profileId, feignClient.read(profileId).getBody()).getBody());
            return ResponseEntity.ok(result.values());
        }
        return ResponseEntity.ok(service.update(id, accountDetails));
    }

    /**
     * @param ids лист технических идентификаторов {@link AccountDetailsEntity}
     * @return {@link ResponseEntity} c {@link List<AccountDetailsDto>}.
     */
    @GetMapping("read/all")
    @Operation(summary = "Получение информации о нескольких банковских счетах по id")
    public ResponseEntity<?> readAll(@RequestParam List<Long> ids,
                                     @RequestParam(value = "profileId", required = false) Long profileId) {
        if (profileId != null) {
            final List<AccountDetailsDto> entityList = service.findAllById(ids);
            final List<Collection<Object>> result = entityList.stream()
                    .map(accountDetailsDto -> {
                        final Map<Long, Object> res = new HashMap<>();
                        res.put(1L, accountDetailsDto);
                        res.put(2L, feignClient.read(accountDetailsDto.getProfileId()).getBody());
                        return res.values();
                    }).collect(Collectors.toList());
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(service.findAllById(ids));
    }
}
