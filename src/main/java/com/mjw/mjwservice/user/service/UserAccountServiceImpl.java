package com.mjw.mjwservice.user.service;

import com.mjw.mjwservice.security.model.ProfileResponse;
import com.mjw.mjwservice.security.model.Token;
import com.mjw.mjwservice.security.service.TokenProvider;
import com.mjw.mjwservice.security.util.CookieUtil;
import com.mjw.mjwservice.user.entity.UserInfoDb;
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

import static com.mjw.mjwservice.security.model.ProfileResponse.SuccessFailure.SUCCESS;

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
    public ResponseEntity<ProfileResponse> registerUser(final UserInfo userInfo) {
        //validationOrchestrator.validate(userInfo, REGISTER_USER);
        final UserInfo userInfoWithEncodedPassword = userInfo.withPassword(passwordEncoder.encode(userInfo.password()));
        final UserInfoDb userInfoDatabase =
                userRepository.save(userInfoMapper.toDatabase(userInfoWithEncodedPassword));
        final HttpHeaders responseHeaders = new HttpHeaders();
        final Token newAccessToken = tokenProvider.generateAccessToken(userInfoDatabase);
        addAccessTokenCookie(responseHeaders, newAccessToken);
        final ProfileResponse profileResponse = userInfoMapper.toProfileResponse(userInfoDatabase)
                .withMessage("Registration successful. Tokens are created in cookie.")
                .withSuccessFailure(SUCCESS);
        return ResponseEntity.ok().headers(responseHeaders).body(profileResponse);
    }

    @Override
    public ResponseEntity<ProfileResponse> login(final UserInfo userInfo, final String accessToken) {
        final String email = userInfo.email();
        final UserInfoDb user = userRepository.findByEmail(email)
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
        final ProfileResponse profileResponse = userInfoMapper.toProfileResponse(user)
                .withMessage("Login successful. Tokens are created in cookie.")
                .withSuccessFailure(SUCCESS);
        return ResponseEntity.ok().headers(responseHeaders).body(profileResponse);
    }

    @Override
    public ProfileResponse getUserProfile() {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UserInfoDb userInfoDatabase = (UserInfoDb) authentication.getPrincipal();

        final UserInfoDb userInfoDatabase1 =
                userRepository.findById(userInfoDatabase.getId()).orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with id: " + userInfoDatabase.getId()));

        return userInfoMapper.toProfileResponse(userInfoDatabase1);
    }

    @Override
    public ResponseEntity<String> logout() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.deleteAccessTokenCookie().toString());
        return ResponseEntity.ok().headers(httpHeaders).body("Logout successful. Tokens are removed from cookie.");
    }

    private void addAccessTokenCookie(final HttpHeaders httpHeaders, final Token token) {
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieUtil.createAccessTokenCookie(token.tokenValue(),
                        token.duration())
                .toString());
    }



}


