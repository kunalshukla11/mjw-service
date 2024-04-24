package com.mjw.mjwservice.service;

import com.mjw.mjwservice.entity.UserInfoDatabaseImpl;
import com.mjw.mjwservice.mapper.UserInfoMapper;
import com.mjw.mjwservice.repository.UserRepository;
import com.mjw.mjwservice.users.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserInfoMapper userInfoMapper;

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
