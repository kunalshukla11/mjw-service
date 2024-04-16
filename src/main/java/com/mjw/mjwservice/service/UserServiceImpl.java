package com.mjw.mjwservice.service;

import com.mjw.mjwservice.entity.UserInfoDatabaseImpl;
import com.mjw.mjwservice.repository.UserRepository;
import com.mjw.mjwservice.users.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public UserInfo registerUser(final UserInfo userInfo) {
    // TODO: create Mapper to this
    final UserInfoDatabaseImpl build =
        UserInfoDatabaseImpl.builder()
            .name(userInfo.name())
            .email(userInfo.email())
            .password(userInfo.password())
            .firstName(userInfo.firstName())
            .lastName(userInfo.lastName())
            .build();

    userRepository.save(build);
    return userInfo;
  }

  @Override
  public UserInfo loginUser(final UserInfo userInfo) {
    return null;
  }
}
