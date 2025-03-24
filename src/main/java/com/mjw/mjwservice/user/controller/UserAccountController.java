package com.mjw.mjwservice.user.controller;

import com.mjw.mjwservice.security.model.ProfileResponse;
import com.mjw.mjwservice.user.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
@Log4j2
public class UserAccountController {

    private final UserAccountService userAccountService;

    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> me() {
        return ResponseEntity.ok(userAccountService.getUserProfile());
    }
}


