package com.mjw.mjwservice.user.model;

import com.mjw.mjwservice.validation.model.UserRegister;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserInfo(
        Long id,
        @Email(message = "email must be valid")
        @NotNull(message = "email cannot be null", groups = {UserRegister.class})
        String email,
        String password,
        String firstName,
        String lastName) implements Validatable {

}
