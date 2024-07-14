package com.mjw.mjwservice.user.controller;

import com.mjw.mjwservice.user.model.UserInfo;
import com.mjw.mjwservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@Log4j2
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserInfo> register(final @RequestBody UserInfo userInfo) {
        log.info("register user: {}", userInfo);
        final UserInfo registerUserInfo = userService.registerUser(userInfo);
        return ResponseEntity.ok(registerUserInfo);
    }

}
