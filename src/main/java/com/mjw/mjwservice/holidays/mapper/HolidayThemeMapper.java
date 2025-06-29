package com.mjw.mjwservice.holidays.mapper;

import com.mjw.mjwservice.holidays.entity.HolidayThemeDb;
import com.mjw.mjwservice.holidays.model.HolidayTheme;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface HolidayThemeMapper {

    public HolidayThemeMapper INSTANCE = Mappers.getMapper(HolidayThemeMapper.class);

    @Mapping(target = "holidayId", source = "holiday.id")
    HolidayTheme toModel(HolidayThemeDb holidayThemeDb);

    HolidayThemeDb toDatabase(HolidayTheme holidayTheme);

}
