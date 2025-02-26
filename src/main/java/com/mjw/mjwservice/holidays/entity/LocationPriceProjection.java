package com.mjw.mjwservice.holidays.entity;

import java.math.BigDecimal;

public interface LocationPriceProjection {

    String getType();

    String getDisplayName();

    String getCityCode();

    String getStateCode();

    String getCountryCode();

    BigDecimal getStandardPrice();

}
