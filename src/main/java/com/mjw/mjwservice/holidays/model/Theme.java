package com.mjw.mjwservice.holidays.model;

import com.mjw.mjwservice.common.utility.ClientModel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ClientModel
public enum Theme {
    CULTURE("Culture"),
    ADVENTURE("Adventure"),
    BEACH("Beach"),
    CRUISE("Cruise"),
    FAMILY("Family"),
    FOOD("Food"),
    LUXURY("Luxury"),
    NATURE("Nature"),
    STAYCATION("Staycation"),
    SHOPPING("Shopping"),
    SKIING("Skiing"),
    SPORTS("Sports"),
    WELLNESS("Wellness"),
    PILGRIMAGE("Pilgrimage"),
    PARTY("Party"),
    WEDDING("Wedding"),
    HONEYMOON("Honeymoon"),;
    private final String diplayName;
}
