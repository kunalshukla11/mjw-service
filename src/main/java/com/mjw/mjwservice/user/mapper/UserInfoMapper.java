package com.mjw.mjwservice.user.mapper;

import com.mjw.mjwservice.user.entity.UserInfoDatabaseImpl;
import com.mjw.mjwservice.user.model.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {

    UserInfoMapper INSTANCE = Mappers.getMapper(UserInfoMapper.class);

    UserInfoDatabaseImpl toDatabase(UserInfo userInfo);

    UserInfo fromDatabase(UserInfoDatabaseImpl userInfoDatabase);

    UserInfo.UserInfoSummery toUserSummery(UserInfoDatabaseImpl userInfoDatabase);

}
