package com.mjw.mjwservice.holidays.mapper;

import com.mjw.mjwservice.holidays.entity.CategoryDb;
import com.mjw.mjwservice.holidays.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    public HolidayMapper INSTANCE = Mappers.getMapper(HolidayMapper.class);

    Category toModel(CategoryDb categoryDb);

    CategoryDb toDatabase(Category category);

}
