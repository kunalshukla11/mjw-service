package com.mjw.mjwservice.user.service;

import com.mjw.mjwservice.user.entity.UserInfoDatabaseImpl;
import com.mjw.mjwservice.user.mapper.UserInfoMapper;
import com.mjw.mjwservice.user.model.UserInfo;
import com.mjw.mjwservice.user.repository.UserRepository;
import com.mjw.mjwservice.validation.service.ValidationOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.mjw.mjwservice.validation.model.ValidationMode.REGISTER_USER;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserInfoMapper userInfoMapper;
    private final ValidationOrchestrator validationOrchestrator;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserInfoDatabaseImpl registerUser(final UserInfo userInfo) {
        validationOrchestrator.validate(userInfo, REGISTER_USER);
        final UserInfo userInfoWithEncodedPassword = userInfo.withPassword(passwordEncoder.encode(userInfo.password()));
        return userRepository.save(userInfoMapper.toDatabase(userInfoWithEncodedPassword));
    }

    @Override
    public UserInfoDatabaseImpl authenticate(final UserInfo userInfo) {
        return Optional.ofNullable(authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(userInfo.email(), userInfo.password())))
                .map(authentication -> userRepository.findByEmail(userInfo.email()))
                .orElseThrow();
    }

}


