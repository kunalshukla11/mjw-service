package com.mjw.mjwservice.holidays.mapper;

import com.mjw.mjwservice.holidays.entity.HolidayDb;
import com.mjw.mjwservice.holidays.entity.HolidayThemeDb;
import com.mjw.mjwservice.holidays.model.Holiday;
import com.mjw.mjwservice.holidays.model.HolidayTheme;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface HolidayMapper {

    public HolidayMapper INSTANCE = Mappers.getMapper(HolidayMapper.class);

    @Mapping(target = "holidayThemes", source = "holidayThemes", qualifiedByName = "mapHolidayThemesFromDb")
    Holiday toModel(HolidayDb holidayDb);

    @Mapping(target = "holidayThemes", source = "holidayThemes", qualifiedByName = "mapHolidayThemes")
    HolidayDb toDatabase(Holiday holiday);

    Holiday merge(Holiday source, @MappingTarget Holiday.HolidayBuilder target);

    @Named(value = "mapHolidayThemesFromDb")
    default Set<HolidayTheme> mapHolidayThemesFromDb(Set<HolidayThemeDb> holidayThemeDbSet) {
        if (holidayThemeDbSet == null) {
            return null;
        }
        return holidayThemeDbSet.stream()
                .map(HolidayThemeMapper.INSTANCE::toModel)
                .collect(java.util.stream.Collectors.toSet());
    }

    @Named(value = "mapHolidayThemes")
    default Set<HolidayThemeDb> mapHolidayThemes(Set<HolidayTheme> holidayThemes) {
        if (holidayThemes == null) {
            return null;
        }
        return holidayThemes.stream()
                .map(HolidayThemeMapper.INSTANCE::toDatabase)
                .collect(java.util.stream.Collectors.toCollection(java.util.LinkedHashSet::new));
    }

}
