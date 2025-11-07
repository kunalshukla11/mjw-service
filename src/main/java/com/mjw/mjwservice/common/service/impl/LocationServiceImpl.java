package com.mjw.mjwservice.common.service.impl;

import com.google.common.base.Strings;
import com.mjw.mjwservice.common.entity.LocationDb;
import com.mjw.mjwservice.common.mapper.LocationMapper;
import com.mjw.mjwservice.common.model.DisplayTarget;
import com.mjw.mjwservice.common.model.Location;
import com.mjw.mjwservice.common.repository.LocationRepository;
import com.mjw.mjwservice.common.service.LocationService;
import com.mjw.mjwservice.holidays.model.HolidaySearchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Override
    public Location save(final Location location) {
        final LocationDb locationDb = locationRepository.save(locationMapper.toDatabase(location));
        return locationMapper.toModel(locationDb);
    }


    @Override
    public Set<Location> saveAll(final Set<Location> locations) {
        log.info("save locations: {}", locations);

        final Set<LocationDb> locationDbList = locations.stream()
                .map(locationMapper::toDatabase)
                .collect(Collectors.toSet());
        final List<LocationDb> locationDbs = locationRepository.saveAll(locationDbList);
        return locationDbs.stream().map(locationMapper::toModel).collect(Collectors.toSet());
    }

    @Override
    public Location getLocationById(final Long id) {
        return locationMapper.toModel(locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with id: " + id)));
    }

    @Override
    public String fetchHeroImage(final String cityCode, final String stateCode, final String countryCode,
                                 final DisplayTarget displayTarget) {
        return Arrays.stream(locationRepository
                .getImagesUrlByCityStateCountry("BOM", "MH", "IN"))
                .findFirst().orElse("");
    }

    @Override
    public String fetchHeroImageByCity(String cityCode, String stateCode, String countryCode) {
        return Arrays.stream(locationRepository
                        .getImagesUrlByCityStateCountry(cityCode, stateCode, countryCode))
                .findFirst().orElse("");
    }

    @Override
    public String fetchHeroImageByState(String stateCode, String countryCode) {
        return Arrays.stream(locationRepository
                        .getImagesUrlByStateCountry(stateCode, countryCode))
                .findFirst().orElse("");
    }

    @Override
    public String fetchHeroImageByCountry(String countryCode) {
        return Arrays.stream(locationRepository
                        .getImagesUrlByCountry(countryCode))
                .findFirst().orElse("");
    }

}
