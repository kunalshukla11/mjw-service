package com.mjw.mjwservice.common.model.dashboard.config;

import com.mjw.mjwservice.common.model.dashboard.Section;
import lombok.Builder;

import java.util.List;

@Builder
public record DashboardConfig(
        Type type,

        Section section,

        List<DashboardData> dashboardData

) {


    public enum Type {
        HOLIDAYS
    }


}
