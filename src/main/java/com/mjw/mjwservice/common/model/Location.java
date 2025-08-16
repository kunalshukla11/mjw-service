package com.mjw.mjwservice.common.model;

import com.mjw.mjwservice.user.model.Validatable;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.springframework.data.annotation.Id;

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
        @NotBlank
        String cityImageUrl,
        String stateImageUrl,
        String countryImageUrl
) implements Validatable {

}
