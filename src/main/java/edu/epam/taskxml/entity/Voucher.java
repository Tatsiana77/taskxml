package edu.epam.taskxml.entity;

import java.util.Locale;

public enum Voucher {
    TOURIST_VOUCHERS,
    BEACH_VACATION_VOUCHER,
    PILGRIMAGE_VOUCHER,
    ID,
    WEB_SITE,
    HOTEL,
    COUNTRY,
    DEPARTURE_DATE_TIME,
    ARRIVAL_DATE_TIME,
    TRANSPORT_TYPE,
    COST,
    DISTANCE_TO_BEACH,
    PILGRIMAGE_PASSPORT_REQUIRED,
    STARS,
    FOOD,
    PLACE_COUNT,
    AIR_CONDITIONING,
    TV;

    private static final char HYPHEN = '-';
    private static final char UNDERSCORE = '_';

    @Override
    public String toString() {
        String result = this.name();
        result = result.toLowerCase(Locale.ROOT);
        result = result.replace(UNDERSCORE, HYPHEN);
        return result;
    }
}
