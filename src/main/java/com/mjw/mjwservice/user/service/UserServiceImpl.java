package com.mjw.mjwservice.user.service;

import com.mjw.mjwservice.exception.ValidationException;
import com.mjw.mjwservice.user.entity.UserInfoDatabaseImpl;
import com.mjw.mjwservice.user.mapper.UserInfoMapper;
import com.mjw.mjwservice.user.model.UserInfo;
import com.mjw.mjwservice.user.repository.UserRepository;
import com.mjw.mjwservice.validation.service.ValidationOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.mjw.mjwservice.validation.model.ValidationMode.REGISTER_USER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserInfoMapper userInfoMapper;
    private final ValidationOrchestrator validationOrchestrator;

    @Override
    public UserInfo registerUser(final UserInfo userInfo) {
        validationOrchestrator.validate(userInfo, REGISTER_USER);
        final UserInfoDatabaseImpl userInfoDatabase = userRepository.save(userInfoMapper.toDatabase(userInfo));
        return UserInfo.builder().id(userInfoDatabase.getId()).build();
    }

    @Override
    public Long loginUser(final UserInfo userInfo) {
        return Optional.ofNullable(userRepository.findByEmail(userInfo.email()))
                .filter(dbUser -> dbUser.getPassword().equals(userInfo.password()))
                .map(UserInfoDatabaseImpl::getId)
                .orElseThrow(() -> new ValidationException("User Not Found"));
    }

}


