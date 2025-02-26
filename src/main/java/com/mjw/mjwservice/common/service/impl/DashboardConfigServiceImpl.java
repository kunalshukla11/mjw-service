package com.mjw.mjwservice.common.service.impl;

import com.mjw.mjwservice.common.entity.DashboardConfigDb;
import com.mjw.mjwservice.common.mapper.DashboardConfigMapper;
import com.mjw.mjwservice.common.model.dashboard.config.DashboardConfig;
import com.mjw.mjwservice.common.repository.DashboardConfigRepository;
import com.mjw.mjwservice.common.service.DashboardConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DashboardConfigServiceImpl implements DashboardConfigService {


    private final DashboardConfigRepository dashboardConfigRepository;
    private final DashboardConfigMapper dashboardConfigMapper;

    @Override
    public List<DashboardConfig> getByType(final DashboardConfig.Type type) {
        final List<DashboardConfigDb> dashboardConfigDbList =
                dashboardConfigRepository.findAllByType(type);
        return dashboardConfigDbList.stream()
                .map(dashboardConfigMapper::toModel)
                .toList();
    }

}
