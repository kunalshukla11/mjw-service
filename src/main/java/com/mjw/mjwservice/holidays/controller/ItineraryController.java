package com.mjw.mjwservice.holidays.controller;

import com.mjw.mjwservice.holidays.model.Itinerary;
import com.mjw.mjwservice.holidays.service.ItineraryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/itinerary")
@RequiredArgsConstructor
@Log4j2
@Validated
public class ItineraryController {

    private final ItineraryService itineraryService;

    @PostMapping(path = "/save", produces = "application/json", consumes = "application/json")
    public Itinerary saveItinerary(final @RequestBody @Valid Itinerary itinerary) {
        return itineraryService.save(itinerary);
    }

}
