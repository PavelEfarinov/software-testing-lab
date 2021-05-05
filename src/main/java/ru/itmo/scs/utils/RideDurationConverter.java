package ru.itmo.scs.utils;

import java.time.Duration;

public class RideDurationConverter {
    private static final String daysDelimiter = " д.";
    private static final String hoursDelimiter = " ч.";
    private static final String minutesDelimiter = " м.";

    public static Duration rideDurationStringToDuration(String rideDuration) {
        Duration result = Duration.ZERO;
        if (rideDuration.contains(daysDelimiter)) {
            result = result.plus(Duration.ofDays(Integer.parseInt(rideDuration.substring(0, rideDuration.indexOf(daysDelimiter)))));
            rideDuration = rideDuration.substring(rideDuration.indexOf(daysDelimiter + " ") + daysDelimiter.length() + 1);
        }
        if (rideDuration.contains(hoursDelimiter)) {
            result = result.plus(Duration.ofHours(Integer.parseInt(rideDuration.substring(0, rideDuration.indexOf(hoursDelimiter)))));
            rideDuration = rideDuration.substring(rideDuration.indexOf(hoursDelimiter + " ") + hoursDelimiter.length() + 1);
        }

        if (rideDuration.contains(minutesDelimiter)) {
            result = result.plus(Duration.ofMinutes(Integer.parseInt(rideDuration.substring(0, rideDuration.indexOf(minutesDelimiter)))));
            rideDuration = rideDuration.substring(rideDuration.indexOf(minutesDelimiter + " ") + minutesDelimiter.length() + 1);
        }
        return result;
    }

}
