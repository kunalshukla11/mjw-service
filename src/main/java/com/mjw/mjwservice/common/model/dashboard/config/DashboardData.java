package com.mjw.mjwservice.common.model.dashboard.config;

import lombok.Builder;
import lombok.With;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record DashboardData(@With
                            String displayName,
                            @With
                            BigDecimal price,
                            Integer order,
                            String cityCode,
                            String stateCode,
                            String countryCode,
                            String imageUrl,
                            DisplayTarget displayTarget,
                            Long holidayId) {

    public enum DisplayTarget {
        CITY,
        STATE,
        COUNTRY
    }

}
