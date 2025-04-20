package com.mjw.mjwservice.common.model;

import com.mjw.mjwservice.common.utility.ClientModel;
import lombok.Builder;

import java.time.LocalDate;

@Builder
@ClientModel
public record Review(String comment,
                     int rating,
                     String username,
                     LocalDate date,
                     String place) {

}
