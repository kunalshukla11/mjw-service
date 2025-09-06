package com.mjw.mjwservice.common.service;

import com.mjw.mjwservice.common.model.DisplayTarget;
import com.mjw.mjwservice.common.model.Location;
import com.mjw.mjwservice.holidays.model.HolidaySearchRequest;

import java.util.Set;

public interface LocationService {

    Location save(Location location);

    Set<Location> saveAll(Set<Location> locations);

    Location getLocationById(Long id);

    String fetchHeroImage(String cityCode, String stateCode, String countryCode, DisplayTarget displayTarget);


}
