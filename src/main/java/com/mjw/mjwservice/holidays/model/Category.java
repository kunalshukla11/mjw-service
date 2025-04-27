package com.mjw.mjwservice.holidays.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.springframework.data.annotation.Id;

@Builder
public record Category(@Id
                       Long id,
                       @NotBlank
                       CategoryName name,
                       Long holidayId) {

}
