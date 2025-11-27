package com.mjw.mjwservice.holidays.model;

import com.mjw.mjwservice.common.model.Location;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;

@Builder
public record Itinerary(
        @Id
        @With
        Long id,
        @NotBlank String name,
        @With
        @NotBlank String identifier,
        @NotNull Location location,
        @NotNull Integer duration,
        @NotNull ItineraryDetail itineraryDetail) {

}
