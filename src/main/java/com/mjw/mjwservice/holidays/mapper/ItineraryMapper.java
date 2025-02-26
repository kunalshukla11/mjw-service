package com.mjw.mjwservice.holidays.mapper;

import com.mjw.mjwservice.holidays.entity.ItineraryDb;
import com.mjw.mjwservice.holidays.model.Itinerary;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ItineraryMapper {

    public ItineraryMapper INSTANCE = Mappers.getMapper(ItineraryMapper.class);

    Itinerary toModel(ItineraryDb itineraryDb);

    ItineraryDb toDatabase(Itinerary itinerary);


}
