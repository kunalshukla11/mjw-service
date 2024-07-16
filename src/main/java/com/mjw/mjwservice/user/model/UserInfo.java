package com.mjw.mjwservice.user.model;

import com.mjw.mjwservice.validation.model.group.UserLogin;
import com.mjw.mjwservice.validation.model.group.UserRegister;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record UserInfo(
        Long id,
        @Email(message = "email must be valid", groups = {UserRegister.class, UserLogin.class})
        @NotEmpty(message = "email cannot be null", groups = {UserRegister.class, UserLogin.class})
        String email,

        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
                message = "Must contain one uppercase, one lowercase, one number and one special character", groups = {UserRegister.class})
        @NotNull(message = "password cannot be null", groups = {UserRegister.class, UserLogin.class})
        String password,

        @NotNull(message = "firstName cannot be null", groups = {UserRegister.class})
        String firstName,

        String lastName) implements Validatable {

}
