package com.mjw.mjwservice.user.service;

import com.mjw.mjwservice.user.mapper.UserInfoMapper;
import com.mjw.mjwservice.user.model.UserInfo;
import com.mjw.mjwservice.user.repository.UserRepository;
import com.mjw.mjwservice.validation.service.ValidationOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        userRepository.save(userInfoMapper.toDatabase(userInfo));
        return userInfo;
    }

    @Override
    public UserInfo loginUser(final UserInfo userInfo) {
        System.out.println("in logingin");
        return userInfo;
    }

}
