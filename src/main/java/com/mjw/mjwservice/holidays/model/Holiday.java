package com.mjw.mjwservice.holidays.model;

import com.mjw.mjwservice.common.model.Currency;
import com.mjw.mjwservice.common.model.Location;
import com.mjw.mjwservice.common.utility.ClientModel;
import com.mjw.mjwservice.validation.model.group.HolidayUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Set;

@Builder(toBuilder = true)
@ClientModel
public record Holiday(
        @Id
        @NotNull(groups = {HolidayUpdate.class})
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
        Currency currency,
        @Size(min = 1, max = 1000)
        @With
        Set<HolidayTheme> holidayThemes
) {

}
