package com.mjw.mjwservice.holidays.model;

import com.mjw.mjwservice.common.utility.ClientModel;

@ClientModel
public record HolidaySearchRequest(String cityCode,
                                   String stateCode,
                                   String countryCode,
                                   Theme theme){

}
