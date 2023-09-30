package com.bank.account.feign;

import com.bank.account.dto.profile.ProfileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "PROFILE-APP", url = "http://localhost:8089/api/profile/profile")
public interface ProfileFeignClient {

    @GetMapping("/read/{profileId}")
    ResponseEntity<ProfileDto> read(@PathVariable("profileId") Long profileId);

    @PostMapping("/create")
    ResponseEntity<ProfileDto> create(@RequestBody ProfileDto profileDto);

    @PutMapping("/update/{profileId}")
    ResponseEntity<ProfileDto> update(@PathVariable("profileId") Long profileId, @RequestBody ProfileDto profileDto);

    @GetMapping("read/all")
    ResponseEntity<List<ProfileDto>> readAllById(@RequestParam List<Long> ids);

}
