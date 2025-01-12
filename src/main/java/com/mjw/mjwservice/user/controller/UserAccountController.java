package com.mjw.mjwservice.user.controller;

import com.mjw.mjwservice.user.model.UserInfo;
import com.mjw.mjwservice.user.service.UserAccountService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<UserInfo.UserInfoSummery> me(final HttpServletRequest request) {
        final String cookieHeader = request.getHeader("Cookie");
        log.info("Cookies received: {}", cookieHeader);
        return ResponseEntity.ok(userAccountService.getUserProfile());
    }
}


