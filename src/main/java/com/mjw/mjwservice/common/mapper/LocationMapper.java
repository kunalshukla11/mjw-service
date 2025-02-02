package com.mjw.mjwservice.common.mapper;

import com.mjw.mjwservice.common.entity.LocationDb;
import com.mjw.mjwservice.common.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);


    LocationDb toDatabase(Location location);

    Location toModel(LocationDb locationDb);

}
