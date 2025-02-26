package com.mjw.mjwservice.common.model.dashboard;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Section {

    TOP_DESTINATIONS("Top Destinations"),
    TOP_PACKAGES("Top Packages"),
    INTERNATIONAL_DESTINATIONS("International Destinations");

    private final String description;


}
