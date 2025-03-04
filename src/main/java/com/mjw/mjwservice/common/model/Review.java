package com.mjw.mjwservice.common.model;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record Review(String comment,
                     int rating,
                     String username,
                     LocalDate date,
                     String place) {

}
