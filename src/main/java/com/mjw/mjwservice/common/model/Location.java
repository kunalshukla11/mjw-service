package com.mjw.mjwservice.common.model;

import com.mjw.mjwservice.common.utility.ClientModel;
import com.mjw.mjwservice.user.model.Validatable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Builder
public record Location(

        @Id
        Long id,
        @NotBlank
        String city,
        @NotBlank
        String cityCode,
        @NotBlank
        String state,
        @NotBlank
        String stateCode,
        @NotBlank
        String country,
        @NotBlank
        String countryCode,
        Type type,
        @NotEmpty
        List<String> imagesUrl) implements Validatable {

    @RequiredArgsConstructor
    @ClientModel
    public enum Type {
        CITY,
        STATE,
        COUNTRY
    }

}
