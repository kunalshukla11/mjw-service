package com.mjw.mjwservice.common.model.dashboard;

import com.mjw.mjwservice.common.model.Review;
import com.mjw.mjwservice.common.model.dashboard.config.DashboardData;
import lombok.Builder;

import java.util.List;

@Builder
public record HolidayDashboard(
        String heroImageUrl,
        List<DashboardData> topDestinations,
        List<DashboardData> topPackages,
        List<DashboardData> internationalDestinations,

        List<DashboardData> holidayThemes,
        List<Review> reviews) {


}
