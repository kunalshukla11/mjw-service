package com.mjw.mjwservice.user.mapper;

import com.mjw.mjwservice.security.model.ProfileResponse;
import com.mjw.mjwservice.user.entity.UserInfoDb;
import com.mjw.mjwservice.user.model.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {

    UserInfoMapper INSTANCE = Mappers.getMapper(UserInfoMapper.class);

    UserInfoDb toDatabase(UserInfo userInfo);

    UserInfo fromDatabase(UserInfoDb userInfoDatabase);

    @Mapping(target = "initial", source = ".", qualifiedByName = "toInitial")
    ProfileResponse toProfileResponse(UserInfoDb userInfoDatabase);

    @Named("toInitial")
    default String translateToFullName(final UserInfoDb userInfoDatabase) {
        final Optional<Character> emailInitial = Optional.ofNullable(userInfoDatabase.getEmail())
                .map(string -> string.charAt(0))
                .filter(Character::isLetter)
                .map(Character::toUpperCase);

        final Optional<Character> nameInitial = Optional.ofNullable(userInfoDatabase.getFirstName())
                .map(string -> string.charAt(0))
                .filter(Character::isLetter)
                .map(Character::toUpperCase);

        return emailInitial
                .or(() -> nameInitial)
                .map(String::valueOf)
                .orElse(null);
    }

}
