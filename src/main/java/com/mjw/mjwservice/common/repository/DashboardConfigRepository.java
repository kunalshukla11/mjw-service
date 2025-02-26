package com.mjw.mjwservice.common.repository;

import com.mjw.mjwservice.common.entity.DashboardConfigDb;
import com.mjw.mjwservice.common.model.dashboard.config.DashboardConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardConfigRepository extends JpaRepository<DashboardConfigDb, Long> {


    List<DashboardConfigDb> findAllByType(DashboardConfig.Type type);


}
