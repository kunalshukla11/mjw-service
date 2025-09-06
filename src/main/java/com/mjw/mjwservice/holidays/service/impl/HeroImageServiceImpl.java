package com.mjw.mjwservice.holidays.service.impl;

import com.mjw.mjwservice.common.service.LocationService;
import com.mjw.mjwservice.holidays.model.HolidaySearchRequest;
import com.mjw.mjwservice.holidays.service.HeroImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log4j2
public class HeroImageServiceImpl implements HeroImageService {
    private final LocationService locationService;


    @Override
    public String fetchHeroImage(final HolidaySearchRequest holidaySearchRequest) {

        log.info("fetch hero image for holiday search request: {}", holidaySearchRequest);
        if (Objects.nonNull(holidaySearchRequest.cityCode())) {

        } else if (Objects.nonNull(holidaySearchRequest.stateCode())) {

        } else if (Objects.nonNull(holidaySearchRequest.countryCode())) {

        } else {
            return "";
        }
        return "";


    }

}
