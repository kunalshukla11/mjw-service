package com.mjw.mjwservice.holidays.model;

import com.mjw.mjwservice.holidays.entity.HolidayDb;

public interface HasHoliday {

    HolidayDb getHoliday();

    void setHoliday(HolidayDb holidayDb);

}
