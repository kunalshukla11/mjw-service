package com.mjw.mjwservice.holidays.model;

import com.mjw.mjwservice.common.utility.ClientModel;
import lombok.Builder;

import java.util.List;

@ClientModel
@Builder(toBuilder = true)
public record HolidaySearchResponse(String heroImageUrl,
                                    List<Holiday> holidays) {

}
