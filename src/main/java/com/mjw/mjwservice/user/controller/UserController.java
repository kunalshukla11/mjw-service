package com.mjw.mjwservice.user.controller;

import com.mjw.mjwservice.user.model.UserInfo;
import com.mjw.mjwservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserInfo.UserInfoSummery> me() {
        return ResponseEntity.ok(userService.getUserProfile());
    }
}


