package com.mjw.mjwservice.holidays.model;

public record HolidaySearchRequest(String cityCode,
                                   String stateCode,
                                   String countryCode,
                                   CategoryName categoryName){

}
