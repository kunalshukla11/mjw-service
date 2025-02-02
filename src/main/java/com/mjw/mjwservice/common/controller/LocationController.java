package com.mjw.mjwservice.common.controller;

import com.mjw.mjwservice.common.model.Location;
import com.mjw.mjwservice.common.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
@Validated
public class LocationController {

    private final LocationService locationService;


    @PostMapping(path = "/save", produces = "application/json", consumes = "application/json")
    public Location save(final @RequestBody @Valid Location location) {
        return locationService.save(location);

    }

}
