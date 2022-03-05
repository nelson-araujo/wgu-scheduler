package com.nelsonaraujo.wguscheduler.Model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.TextStyle;
import java.util.*;

public class TimeZones {

    /** Build list of all available time zones.
     *
     * @return Sorted list of all available time zones.
     */
    public static List<String> getTimeZonesList() {
        Set<String> allTimeZonesSet = ZoneId.getAvailableZoneIds();
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<String> allTimeZonesListSorted = new ArrayList<String>();

        for (String id : allTimeZonesSet) {
            ZoneId zoneId = ZoneId.of(id);
            ZoneOffset zonedOffSet = zoneId.getRules().getOffset(currentDateTime);

            // Replace zulu identifier (Z) with 00:00
            if (zonedOffSet.getId().toString().equals("Z")) {
                allTimeZonesListSorted.add("(GMT +00:00) " + zoneId.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " [" + zoneId + "]");
            } else {
                allTimeZonesListSorted.add("(GMT " + zonedOffSet + ") " + zoneId.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " [" + zoneId + "]");
            }
        }

        // Sort timezones list
        Collections.sort(allTimeZonesListSorted);

        return allTimeZonesListSorted;
    }

}
