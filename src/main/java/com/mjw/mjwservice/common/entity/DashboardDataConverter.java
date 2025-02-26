package com.mjw.mjwservice.common.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mjw.mjwservice.common.model.dashboard.config.DashboardData;
import com.mjw.mjwservice.common.utility.JsonUtil;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.SneakyThrows;

import java.util.Collections;
import java.util.List;

@Converter(autoApply = true)
public class DashboardDataConverter implements AttributeConverter<List<DashboardData>, String> {

    @Override
    @SneakyThrows
    public String convertToDatabaseColumn(final List<DashboardData> dashboardDataList) {
        if (dashboardDataList == null || dashboardDataList.isEmpty()) {
            return "[]";
        }
        return JsonUtil.jsonStringRepresentation(dashboardDataList);
    }

    @Override
    @SneakyThrows
    public List<DashboardData> convertToEntityAttribute(final String json) {
        if (json == null || json.isEmpty()) {
            return Collections.emptyList();
        }
        return JsonUtil.fromObject(JsonUtil.fromJsonString(json, new TypeReference<List<DashboardData>>() {
        }), new TypeReference<>() {
        });
    }

}

