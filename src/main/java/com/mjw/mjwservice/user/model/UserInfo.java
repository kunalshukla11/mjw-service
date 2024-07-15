package com.mjw.mjwservice.user.model;

import com.mjw.mjwservice.validation.model.UserRegister;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record UserInfo(
        Long id,
        @Email(message = "email must be valid")
        @NotNull(message = "email cannot be null", groups = {UserRegister.class})
        String email,

        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
                message = "password must be valid")
        @NotNull(message = "password cannot be null", groups = {UserRegister.class})
        String password,

        @NotNull(message = "firstName cannot be null", groups = {UserRegister.class})
        String firstName,

        String lastName) implements Validatable {

}
