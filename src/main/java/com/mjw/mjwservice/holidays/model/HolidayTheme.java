package com.mjw.mjwservice.holidays.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.springframework.data.annotation.Id;

@Builder
public record HolidayTheme(@Id
                           Long id,
                           @NotBlank
                           Theme theme,
                           Long holidayId) {

}
