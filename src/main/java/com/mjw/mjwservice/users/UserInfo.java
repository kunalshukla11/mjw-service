package com.mjw.mjwservice.users;

import lombok.Builder;

@Builder
public record UserInfo(
    Long id, String name, String email, String password, String firstName, String lastName) {}
