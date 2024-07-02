package com.mjw.mjwservice.user.service;

import com.mjw.mjwservice.user.entity.UserInfoDatabaseImpl;
import com.mjw.mjwservice.user.mapper.UserInfoMapper;
import com.mjw.mjwservice.user.model.UserInfo;
import com.mjw.mjwservice.user.repository.UserRepository;
import com.mjw.mjwservice.validation.service.GenericValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserInfoMapper userInfoMapper;
    private final GenericValidationService<UserInfo> validationService;

    @Override
    public UserInfo registerUser(final UserInfo userInfo) {
        final UserInfoDatabaseImpl build = userInfoMapper.toDatabase(userInfo);
        userRepository.save(build);
        return userInfo;
    }

    @Override
    public UserInfo loginUser(final UserInfo userInfo) {
        return null;
    }
}
