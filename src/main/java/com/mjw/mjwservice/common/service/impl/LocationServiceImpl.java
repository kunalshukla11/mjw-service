package com.mjw.mjwservice.common.service.impl;

import com.mjw.mjwservice.common.entity.LocationDb;
import com.mjw.mjwservice.common.mapper.LocationMapper;
import com.mjw.mjwservice.common.model.Location;
import com.mjw.mjwservice.common.repository.LocationRepository;
import com.mjw.mjwservice.common.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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

}
