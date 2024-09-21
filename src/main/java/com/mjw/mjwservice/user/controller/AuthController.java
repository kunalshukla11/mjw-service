package com.mjw.mjwservice.user.controller;

import com.mjw.mjwservice.common.model.LoginResponse;
import com.mjw.mjwservice.common.util.SecurityCipher;
import com.mjw.mjwservice.user.model.UserInfo;
import com.mjw.mjwservice.user.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
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

    private final UserAccountService userAccountService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(final @RequestBody UserInfo userInfo) {
        log.info("register user: {}", userInfo);
        return userAccountService.registerUser(userInfo);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(final @RequestBody UserInfo userInfo, final @CookieValue(name =
            "auth_token",
            required = false) String accessToken) {
        log.info("login user: {}", userInfo);
        final Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userInfo.email(),
                        userInfo.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String decryptedAccessToken = SecurityCipher.decrypt(accessToken);
        return userAccountService.login(userInfo, decryptedAccessToken);

    }


}