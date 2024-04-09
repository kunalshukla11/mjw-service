package com.mjw.mjwservice.users.controller;


import com.mjw.mjwservice.service.UserService;
import com.mjw.mjwservice.users.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@Log4j2
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserInfo> register(@RequestBody UserInfo userInfo) {
        log.info("register user: {}", userInfo);

        UserInfo registerUserInfo = userService.registerUser(userInfo);
        return ResponseEntity.ok(registerUserInfo);
    }
}
