package com.mjw.mjwservice.holidays.mapper;

import com.mjw.mjwservice.holidays.entity.HolidayDb;
import com.mjw.mjwservice.holidays.model.Holiday;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface HolidayMapper {

    public HolidayMapper INSTANCE = Mappers.getMapper(HolidayMapper.class);


    Holiday toModel(HolidayDb holidayDb);

    HolidayDb toDatabase(Holiday holiday);

    Holiday merge(Holiday source, @MappingTarget Holiday.HolidayBuilder target);

}
