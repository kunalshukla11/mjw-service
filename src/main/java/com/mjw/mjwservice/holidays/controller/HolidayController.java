package com.mjw.mjwservice.holidays.controller;

import com.mjw.mjwservice.holidays.model.Holiday;
import com.mjw.mjwservice.common.model.dashboard.HolidayDashboard;
import com.mjw.mjwservice.holidays.service.HolidayService;
import com.mjw.mjwservice.holidays.service.ItineraryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/holiday")
@RequiredArgsConstructor
@Log4j2
public class HolidayController {

    private final HolidayService holidayService;
    private final ItineraryService itineraryService;


    @PostMapping(path = "/save", produces = "application/json", consumes = "application/json")
    public Holiday save(final @RequestBody @Valid Holiday holiday) {
        return holidayService.save(holiday);
    }

    @PostMapping(path = "/saveAll", produces = "application/json", consumes = "application/json")
    public List<Holiday> saveAll(final @RequestBody @Valid List<Holiday> holidays) {
        return holidayService.saveAll(holidays);
    }

    @GetMapping(path = "/holiday-dashboard", produces = "application/json")
    @Cacheable("holiday-dashboard")
    public HolidayDashboard holidayDashboard() {
        return holidayService.holidayDashboard();
    }

    @CacheEvict(value = "holiday-dashboard", allEntries = true)
    @GetMapping(path = "/refresh-dashboard")
    public String refreshDashboard() {
        return  "refreshed";
    }

}
