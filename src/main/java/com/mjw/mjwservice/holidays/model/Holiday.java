package com.mjw.mjwservice.holidays.model;

import com.mjw.mjwservice.common.model.Currency;
import com.mjw.mjwservice.common.model.Location;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Builder
public record Holiday(
        @Id
        Long id,
        @NotBlank
        String name,

        @With
        Location location,
        @NotNull
        @With
        Itinerary itinerary,
        @NotNull
        BigDecimal standardPrice,
        @NotNull
        BigDecimal superiorPrice,
        @NotNull
        BigDecimal luxuryPrice,

        @NotNull
        Currency currency
) {

}
