package com.mjw.mjwservice.holidays.entity;

import com.mjw.mjwservice.common.utility.JsonUtil;
import com.mjw.mjwservice.holidays.model.ItineraryDetail;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.SneakyThrows;

@Converter(autoApply = true)
public class ItineraryDetailConverter implements AttributeConverter<ItineraryDetail, String> {

    @Override
    @SneakyThrows
    public String convertToDatabaseColumn(final ItineraryDetail itineraryDetail) {
        if (itineraryDetail == null) {
            return null;
        }
        return JsonUtil.jsonStringRepresentation(itineraryDetail);
    }

    @Override
    @SneakyThrows
    public ItineraryDetail convertToEntityAttribute(final String json) {
        if (json == null) {
            return null;
        }
        return JsonUtil.fromObject(JsonUtil.fromJsonString(json, ItineraryDetail.class), ItineraryDetail.class);
    }

}
