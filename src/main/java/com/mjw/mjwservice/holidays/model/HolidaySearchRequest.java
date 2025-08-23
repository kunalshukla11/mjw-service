package com.mjw.mjwservice.holidays.model;

import com.mjw.mjwservice.common.utility.ClientModel;
import jakarta.annotation.Nullable;

@ClientModel
public record HolidaySearchRequest(
        @Nullable
        String cityCode,
        @Nullable
        String stateCode,
        @Nullable
        String countryCode,
        @Nullable Theme theme) {

}
