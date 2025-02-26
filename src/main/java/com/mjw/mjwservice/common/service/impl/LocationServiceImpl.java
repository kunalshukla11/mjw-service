package com.mjw.mjwservice.common.service.impl;

import com.mjw.mjwservice.common.entity.LocationDb;
import com.mjw.mjwservice.common.mapper.LocationMapper;
import com.mjw.mjwservice.common.model.Location;
import com.mjw.mjwservice.common.repository.LocationRepository;
import com.mjw.mjwservice.common.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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

}
