package com.mjw.mjwservice.common.service;

import com.mjw.mjwservice.common.model.dashboard.config.DashboardConfig;

import java.util.List;

public interface DashboardConfigService {

    List<DashboardConfig> getByType(DashboardConfig.Type type);

}
