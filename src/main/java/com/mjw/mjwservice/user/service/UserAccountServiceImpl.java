package com.mjw.mjwservice.user.service;

import com.mjw.mjwservice.common.model.LoginResponse;
import com.mjw.mjwservice.common.model.Token;
import com.mjw.mjwservice.common.service.TokenProvider;
import com.mjw.mjwservice.common.util.CookieUtil;
import com.mjw.mjwservice.user.entity.UserInfoDatabaseImpl;
import com.mjw.mjwservice.user.mapper.UserInfoMapper;
import com.mjw.mjwservice.user.model.UserInfo;
import com.mjw.mjwservice.user.repository.UserRepository;
import com.mjw.mjwservice.validation.service.ValidationOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.mjw.mjwservice.validation.model.ValidationMode.REGISTER_USER;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final UserRepository userRepository;
    private final UserInfoMapper userInfoMapper;
    private final ValidationOrchestrator validationOrchestrator;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final CookieUtil cookieUtil;

    @Override
    public ResponseEntity<LoginResponse> registerUser(final UserInfo userInfo) {
        validationOrchestrator.validate(userInfo, REGISTER_USER);
        final UserInfo userInfoWithEncodedPassword = userInfo.withPassword(passwordEncoder.encode(userInfo.password()));
        final UserInfoDatabaseImpl userInfoDatabase =
                userRepository.save(userInfoMapper.toDatabase(userInfoWithEncodedPassword));
        final HttpHeaders responseHeaders = new HttpHeaders();
        final Token newAccessToken = tokenProvider.generateAccessToken(userInfoDatabase);
        addAccessTokenCookie(responseHeaders, newAccessToken);
        final LoginResponse loginResponse = LoginResponse.builder()
                .message("Registration successful. Tokens are created in cookie.")
                .successFailure(LoginResponse.SuccessFailure.SUCCESS)
                .userId(userInfoDatabase.getId())
                .build();
        return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
    }

    @Override
    public ResponseEntity<LoginResponse> login(final UserInfo userInfo, final String accessToken) {
        final String email = userInfo.email();
        final UserInfoDatabaseImpl user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        final boolean accessTokenValid = tokenProvider.validateToken(accessToken);


        final HttpHeaders responseHeaders = new HttpHeaders();
        Token newAccessToken;
        if (!accessTokenValid) {
            newAccessToken = tokenProvider.generateAccessToken(user);
            addAccessTokenCookie(responseHeaders, newAccessToken);
        }

        if (accessTokenValid) {
            newAccessToken = tokenProvider.generateAccessToken(user);
            addAccessTokenCookie(responseHeaders, newAccessToken);

        }

        final LoginResponse loginResponse = LoginResponse.builder()
                .message("Login successful. Tokens are created in cookie.")
                .successFailure(LoginResponse.SuccessFailure.SUCCESS)
                .userId(user.getId())
                .build();
        return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
    }

    @Override
    public UserInfo.UserInfoSummery getUserProfile() {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UserInfoDatabaseImpl userInfoDatabase = (UserInfoDatabaseImpl) authentication.getPrincipal();

        final UserInfoDatabaseImpl userInfoDatabase1 =
                userRepository.findById(userInfoDatabase.getId()).orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with id: " + userInfoDatabase.getId()));
        return userInfoMapper.toUserSummery(userInfoDatabase1);
    }

    @Override
    public ResponseEntity<String> logout() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createAccessTokenCookie("", 0L).toString());
        return ResponseEntity.ok().headers(httpHeaders).body("Logout successful. Tokens are removed from cookie.");
    }

    private void addAccessTokenCookie(final HttpHeaders httpHeaders, final Token token) {
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createAccessTokenCookie(token.tokenValue(),
                        token.duration())
                .toString());
    }



}


