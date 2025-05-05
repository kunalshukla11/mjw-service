package com.mjw.mjwservice.holidays.controller;

import com.mjw.mjwservice.holidays.model.Holiday;
import com.mjw.mjwservice.common.model.dashboard.HolidayDashboard;
import com.mjw.mjwservice.holidays.model.HolidaySearchRequest;
import com.mjw.mjwservice.holidays.service.HolidayService;
import com.mjw.mjwservice.holidays.service.ItineraryService;
import com.mjw.mjwservice.validation.model.group.HolidayCreate;
import com.mjw.mjwservice.validation.model.group.HolidayUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    @Validated(HolidayCreate.class)
    public Holiday save(final @RequestBody @Valid Holiday holiday) {
        return holidayService.save(holiday);
    }



    @PutMapping(path = "/update", produces = "application/json", consumes = "application/json")
    @Validated(HolidayUpdate.class)
    public Holiday update(final @RequestBody @Valid Holiday holiday) {
        return holidayService.update(holiday);
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



    @PostMapping(path = "/search-holidays", produces = "application/json")
    public List<Holiday> searchHolidays(@RequestBody final HolidaySearchRequest holidaySearchRequest) {
       log.info("search holidays: {}", holidaySearchRequest);
        return holidayService.searchHolidays(holidaySearchRequest);
    }

    @GetMapping(path = "/get/{id}", produces = "application/json")
    public Holiday get(final @PathVariable Long id) {
        log.info("get holidays by id: {}", id);
        return holidayService.getHolidayById(id);
    }

}
