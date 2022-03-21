package com.nelsonaraujo.wguscheduler.Model;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.TextStyle;
import java.util.*;

public class TimeZones {
    public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String TIME_FORMAT = "HH:mm:ss";

    /**
     * Get all time zones
     * @return String set of all time zones
     */
    public static Set<String> getTimeZones() {
        Set<String> allTimeZonesSet = ZoneId.getAvailableZoneIds();

        return allTimeZonesSet;
    }

    /**
     * Get the current local time.
     * @return local time
     */
    public static Timestamp getCurrentLocalTime(){
        return Timestamp.valueOf(LocalDateTime.now());
    }

    /**
     * Get the current UTC time.
     * @return UTC time
     */
    public static Timestamp getCurrentUtcTimestamp(){
        LocalDateTime utcTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        return Timestamp.valueOf(utcTime);
    }

    public static Timestamp convertTime(Timestamp originalTimestamp, String zoneIdName){
        ZoneId zoneId = ZoneId.of(zoneIdName); // Convert zone id name to ZoneId

        ZonedDateTime utcDateTimeZdt = originalTimestamp.toInstant().atZone(zoneId);

        return Timestamp.valueOf(utcDateTimeZdt.toLocalDateTime());
    }

    /**
     * Get the formatted system time zone.
     * @return Formatted system time zone
     */
    public static String getSystemTimeZoneFormatted(){
        ZoneId systemZoneId = ZoneId.systemDefault();

        return formatTimeZone(String.valueOf(systemZoneId));
    }

    /**
     * Get a list of all time zones formatted.
     * @return Time zones formatted.
     */
    public static List<String> getFormattedTimeZones(){
        Set<String> timeZones = getTimeZones();
        List<String> timeZonesFormatted = new ArrayList<String>();

        for(String timeZone : timeZones){
            timeZonesFormatted.add(formatTimeZone(timeZone));
        }

        Collections.sort(timeZonesFormatted);

        return timeZonesFormatted;

    }

    /**
     * Format a timezone to a more user friendly format. full_name [zoneId] (GMT zonedOffSet)
     * @param timeZone Timezone string to format
     * @return Formatted string
     */
    public static String formatTimeZone(String timeZone){
        LocalDateTime currentDateTime = LocalDateTime.now();

        ZoneId zoneId = ZoneId.of(timeZone);
        ZoneOffset zonedOffSet = zoneId.getRules().getOffset(currentDateTime);

        // Replace zulu identifier (Z) with 00:00
        if (zonedOffSet.getId().toString().equals("Z")) {
            return zoneId.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " [" + zoneId + "]" + " (GMT +00:00) ";
        } else {
            return zoneId.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " [" + zoneId + "]" + " (GMT " + zonedOffSet + ") ";
        }
    }
}
