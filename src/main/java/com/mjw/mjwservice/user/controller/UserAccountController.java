package com.mjw.mjwservice.user.controller;

import com.mjw.mjwservice.user.model.UserInfo;
import com.mjw.mjwservice.user.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;

    @GetMapping("/me")
    public ResponseEntity<UserInfo.UserInfoSummery> me() {
        return ResponseEntity.ok(userAccountService.getUserProfile());
    }
}


