package com.mjw.mjwservice.common.mapper;

import com.mjw.mjwservice.common.entity.DashboardConfigDb;
import com.mjw.mjwservice.common.model.dashboard.config.DashboardConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DashboardConfigMapper {

    DashboardConfigMapper DashboardConfigMapper = Mappers.getMapper(DashboardConfigMapper.class);


    DashboardConfig toModel(DashboardConfigDb dashboardConfigDb);

}
