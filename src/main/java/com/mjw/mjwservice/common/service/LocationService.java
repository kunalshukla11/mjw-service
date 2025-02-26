package com.mjw.mjwservice.common.service;

import com.mjw.mjwservice.common.model.Location;

import java.util.Set;

public interface LocationService {

    Location save(Location location);

    Set<Location> saveAll(Set<Location> locations);

    Location getLocationById(Long id);


}
