package com.mjw.mjwservice.user.controller;

import com.mjw.mjwservice.common.service.JwtService;
import com.mjw.mjwservice.user.entity.UserInfoDatabaseImpl;
import com.mjw.mjwservice.user.model.UserInfo;
import com.mjw.mjwservice.user.service.AuthenticationService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
@Log4j2
@Validated
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<Long> register(final @RequestBody UserInfo userInfo) {
        log.info("register user: {}", userInfo);
        final UserInfoDatabaseImpl registerUserInfo = authenticationService.registerUser(userInfo);
        final String token = jwtService.generateToken(registerUserInfo);
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, "jwt=" + token + "; HttpOnly; Secure; SameSite=None");
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(registerUserInfo.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<Long> login(final @RequestBody UserInfo userInfo) {
        log.info("login user: {}", userInfo);
        final UserInfoDatabaseImpl authenticatedUser = authenticationService.authenticate(userInfo);
        final String token = jwtService.generateToken(authenticatedUser);

        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, "jwt=" + token + "; HttpOnly; Secure; SameSite=None");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(authenticatedUser.getId());
    }

    @Builder
    public record LoginResponse(
            Long userId,
            String token,
            Long expiresIn
    ) {}

}