package com.mjw.mjwservice.holidays.service;

import com.mjw.mjwservice.holidays.model.Itinerary;


public interface ItineraryService {

    Itinerary save(Itinerary itinerary);

    Itinerary getItineraryById(Long id);

}
