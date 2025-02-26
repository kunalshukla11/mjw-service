package com.mjw.mjwservice.common.entity;

import com.mjw.mjwservice.common.model.dashboard.Section;
import com.mjw.mjwservice.common.model.dashboard.config.DashboardConfig;
import com.mjw.mjwservice.common.model.dashboard.config.DashboardData;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "DASHBOARD_CONFIG")
@Table(name = "DASHBOARD_CONFIG", schema = "MJW_SERVICE")
public class DashboardConfigDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    DashboardConfig.Type type;

    @Column(name = "SECTION")
    @Enumerated(EnumType.STRING)
    Section section;

    @Column(name = "DASHBOARD_DATA", columnDefinition = "JSONB")
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = DashboardDataConverter.class)
    List<DashboardData> dashboardData;

}
