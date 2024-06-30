package com.mjw.mjwservice.mapper;

import com.mjw.mjwservice.entity.UserInfoDatabaseImpl;
import com.mjw.mjwservice.users.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {
  UserInfoMapper INSTANCE = Mappers.getMapper(UserInfoMapper.class);

  UserInfoDatabaseImpl toDatabase(UserInfo userInfo);
}
