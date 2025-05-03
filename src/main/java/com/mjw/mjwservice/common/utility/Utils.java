package com.mjw.mjwservice.common.utility;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;

@UtilityClass
public class Utils {

    public static <T, R> R let(final T value, final Function<T, R> function) {
        return function.apply(value);
    }

    //TODO: Use db trigger or event to generate identifier
    public static String itineraryIdentifier(final String cityCode,
                                             final String countryCode,
                                             final Integer duration,
                                             final String name) {
        return String.join("_", cityCode,
                countryCode, duration.toString(), StringUtils.deleteWhitespace(name));
    }

}
