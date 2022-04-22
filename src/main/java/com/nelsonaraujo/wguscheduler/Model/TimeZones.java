package com.nelsonaraujo.wguscheduler.Model;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.TextStyle;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static Timestamp getLocalTimeFromTimeZone(Timestamp originalTimeTs, String formattedTimeZone){
        ZonedDateTime originalTimeZdt = originalTimeTs.toLocalDateTime().atZone(ZoneId.of(TimeZones.getZoneIdFromFormattedTimeZone(formattedTimeZone)));
        ZonedDateTime localTimeZdt = originalTimeZdt.toInstant().atZone(ZoneId.systemDefault());
        Timestamp localTimeTs = Timestamp.from(localTimeZdt.toInstant());

        return localTimeTs;
    }

    /**
     * Get UTC time from a specified time zone.
     * @param originalTimeTs Specific time.
     * @param formattedTimeZone Time zone the time is in.
     * @return UTC time.
     */
    public static Timestamp getUtcTime(Timestamp originalTimeTs, String formattedTimeZone){
        ZonedDateTime originalTimeZdt = originalTimeTs.toLocalDateTime().atZone(ZoneId.of(TimeZones.getZoneIdFromFormattedTimeZone(formattedTimeZone)));

        ZoneId zoneIdToConvertTo = ZoneId.of("GMT"); // Convert zone id string to ZoneId

        ZonedDateTime convertedDateTimeZdt = originalTimeTs.toInstant().atZone(zoneIdToConvertTo);

        Timestamp utcTimeTs = Timestamp.valueOf(convertedDateTimeZdt.toLocalDateTime());

        return utcTimeTs;
    }

    /**
     * Get the current UTC time.
     * @return UTC time
     */
    public static Timestamp getCurrentUtcTimestamp(){
        LocalDateTime utcTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        return Timestamp.valueOf(utcTime);
    }

    /**
     * Extract the zone id in the formatted string between [ ]
     * @param formattedTimeZone
     * @return zone id
     */
    public static String getZoneIdFromFormattedTimeZone(String formattedTimeZone){
        Pattern zoneIdPattern = Pattern.compile("\\[(.*?)\\]"); // zoneId between [ ]
        Matcher matchedPattern = zoneIdPattern.matcher(formattedTimeZone);

        if(matchedPattern.find()){
            return matchedPattern.group(1); // Return zone id
        }

        return null;
    }

    /**
     * Convert UTC Timestamp to specified time zone.
     * @param utcTimestamp UTC Timestamp.
     * @param zoneNameToConvertTo Time zone to update Timestamp to.
     * @return Timestamp converted to the provided time zone.
     */
    public static Timestamp convertUtcTime(Timestamp utcTimestamp, String zoneNameToConvertTo){
        ZoneId zoneIdToConvertTo = ZoneId.of(zoneNameToConvertTo); // Convert zone id string to ZoneId

        ZonedDateTime convertedDateTimeZdt = utcTimestamp.toInstant().atZone(zoneIdToConvertTo);

        return Timestamp.valueOf(convertedDateTimeZdt.toLocalDateTime());
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
