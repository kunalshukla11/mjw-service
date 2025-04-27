package com.mjw.mjwservice.holidays.service;

import com.mjw.mjwservice.common.model.dashboard.HolidayDashboard;
import com.mjw.mjwservice.holidays.model.Holiday;

import java.util.List;


public interface HolidayService {

    Holiday save(Holiday holiday);

    Holiday update(Holiday holiday);

    List<Holiday> saveAll(List<Holiday> holidays);

    HolidayDashboard holidayDashboard();

    List<Holiday> getHolidays(String cityCode, String stateCode, String countryCode, String themeCode);

    Holiday getHolidayById(Long id);

}
