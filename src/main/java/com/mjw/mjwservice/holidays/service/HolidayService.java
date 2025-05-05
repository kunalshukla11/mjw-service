package com.mjw.mjwservice.holidays.service;

import com.mjw.mjwservice.common.model.dashboard.HolidayDashboard;
import com.mjw.mjwservice.holidays.model.Holiday;
import com.mjw.mjwservice.holidays.model.HolidaySearchRequest;

import java.util.List;


public interface HolidayService {

    Holiday save(Holiday holiday);

    Holiday update(Holiday holiday);

    List<Holiday> saveAll(List<Holiday> holidays);

    HolidayDashboard holidayDashboard();

    List<Holiday> searchHolidays(HolidaySearchRequest holidaySearchRequest);

    Holiday getHolidayById(Long id);

}
