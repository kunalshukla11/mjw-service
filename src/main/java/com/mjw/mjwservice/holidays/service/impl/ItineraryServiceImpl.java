package com.mjw.mjwservice.holidays.service.impl;

import com.mjw.mjwservice.common.model.Location;
import com.mjw.mjwservice.common.service.LocationService;
import com.mjw.mjwservice.common.utility.Utils;
import com.mjw.mjwservice.holidays.entity.ItineraryDb;
import com.mjw.mjwservice.holidays.mapper.ItineraryMapper;
import com.mjw.mjwservice.holidays.model.Itinerary;
import com.mjw.mjwservice.holidays.repository.ItineraryRepository;
import com.mjw.mjwservice.holidays.service.ItineraryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ItineraryServiceImpl implements ItineraryService {

    private final ItineraryRepository itineraryRepository;
    private final ItineraryMapper itineraryMapper;
    private final LocationService locationService;

    @Transactional
    public Itinerary save(final Itinerary itinerary) {
        log.info("save itinerary: {}", itinerary);
        final Location location = locationService.getLocationById(Optional.ofNullable(itinerary.location())
                .map(Location::id)
                .orElseThrow());
        final Itinerary updatedItinerary = itinerary.withIdentifier(
                Utils.itineraryIdentifier(
                        location.cityCode(),
                        location.countryCode(),
                        itinerary.duration(),
                        itinerary.name()
                ));
        final ItineraryDb itineraryDb = itineraryRepository.save(itineraryMapper.toDatabase(updatedItinerary));
        return itineraryMapper.toModel(itineraryDb);
    }

    @Override
    public Itinerary getItineraryById(final Long id) {
        log.info("get itinerary by id: {}", id);
        final ItineraryDb itineraryDb = itineraryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itinerary not found with id: " + id));

        return itineraryMapper.toModel(itineraryDb);

    }

}
