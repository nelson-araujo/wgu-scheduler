package com.nelsonaraujo.wguscheduler.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.sql.Timestamp;
import java.time.*;
import java.util.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Appointments {

    public static boolean isOverlapAppointment(String customerName, Timestamp aptStart, Timestamp aptEnd, String formattedTimeZone, Integer aptId){
        ZoneId utcZoneId = ZoneId.of("GMT"); // Get US/Eastern Time Zone ZoneId

        // convert start time to UTC ZonedDateTime
        ZonedDateTime aptStartZdt = aptStart.toLocalDateTime().atZone(ZoneId.of(
                TimeZones.getZoneIdFromFormattedTimeZone(formattedTimeZone)));
        ZonedDateTime aptStartUtcZdt = aptStartZdt.toInstant().atZone(utcZoneId);
        Timestamp aptStartUtcTs = Timestamp.from(aptStartUtcZdt.toInstant());

        // convert ent time to UTC ZonedDateTime
        ZonedDateTime aptEndZdt = aptEnd.toLocalDateTime().atZone(ZoneId.of(
                TimeZones.getZoneIdFromFormattedTimeZone(formattedTimeZone)));
        ZonedDateTime aptEndUtcZdt = aptEndZdt.toInstant().atZone(utcZoneId);
        Timestamp aptEndUtcTs = Timestamp.from(aptEndUtcZdt.toInstant());

        Integer customerId = Customers.getCustomerId(customerName);
        List<Appointment> userAppointments = getUserAppointments(customerId);

        for(Appointment appointment : userAppointments){
            ZonedDateTime appointmentStartZdt = ZonedDateTime.from(appointment.getStart().toInstant().atZone(utcZoneId));
            ZonedDateTime appointmentEndZdt = ZonedDateTime.from(appointment.getEnd().toInstant().atZone(utcZoneId));

            if(appointment.getId()!=aptId) {
                if (aptStartUtcZdt.isEqual(appointmentStartZdt)
                        || (aptStartUtcZdt.isAfter(appointmentStartZdt) && aptStartUtcZdt.isBefore(appointmentEndZdt))) {
                    Alert alertMsg = new Alert(Alert.AlertType.INFORMATION);
                    alertMsg.setTitle("Exiting appointment");
                    alertMsg.setHeaderText("An appointment with the same time frame already exists.");
                    alertMsg.showAndWait();
                    return TRUE;
                }
            }
        }

        return FALSE; // todo: update
    }

    /**
     * Check if the date selected does not fall on the weekend.
     * @param aptTime appointment time with date.
     * @param formattedTimeZone Selected timezone
     * @return If it falls during the week.
     */
    public static boolean isNotWeekend(Timestamp aptTime, String formattedTimeZone){
        // Create a ZonedDataTime with the provided time zone.
        ZonedDateTime dateTimeZdt = aptTime.toLocalDateTime().atZone(ZoneId.of(
                TimeZones.getZoneIdFromFormattedTimeZone(formattedTimeZone)));
        ZoneId zoneIdToConvertTo = ZoneId.of("US/Eastern"); // Get US/Eastern Time Zone ZoneId
        ZonedDateTime utcDateTimeZdt = dateTimeZdt.toInstant().atZone(zoneIdToConvertTo);

        // Check is the date time falls in a weekend.
        DayOfWeek dateTimeDayOfWeek = utcDateTimeZdt.toLocalDateTime().toLocalDate().getDayOfWeek();
        if(dateTimeDayOfWeek == DayOfWeek.SATURDAY || dateTimeDayOfWeek == DayOfWeek.SUNDAY){
            Alert alertMsg = new Alert(Alert.AlertType.INFORMATION);
            alertMsg.setTitle("Outside business hours");
            alertMsg.setHeaderText("The Date you selected falls on the weekend.");
            alertMsg.showAndWait();

            return TRUE;
        }
        return FALSE;
    }

    public static boolean isOutsideBusinessHours(Timestamp aptTime, String formattedTimeZone){
        ZonedDateTime dateTimeZdt = aptTime.toLocalDateTime().atZone(ZoneId.of(
                TimeZones.getZoneIdFromFormattedTimeZone(formattedTimeZone)));
        ZoneId usEasternZoneId = ZoneId.of("US/Eastern"); // Get US/Eastern Time Zone ZoneId
        ZonedDateTime aptTimeEstZdt = dateTimeZdt.toInstant().atZone(usEasternZoneId);

        ZonedDateTime aptTimeEstMinZdt = aptTimeEstZdt.with(aptTimeEstZdt.toLocalTime().of(8,0));
        ZonedDateTime aptTimeEstMaxZdt = aptTimeEstZdt.with(aptTimeEstZdt.toLocalTime().of(22,0));

        // Check if between 8:00 and 22:00 EST
        if(aptTimeEstZdt.isBefore(aptTimeEstMinZdt) || aptTimeEstZdt.isAfter(aptTimeEstMaxZdt)){
            Alert alertMsg = new Alert(Alert.AlertType.INFORMATION);
            alertMsg.setTitle("Outside business hours");
            alertMsg.setHeaderText("The time you entered is outside our Monday-Friday 08:00-22:00 business hours.");
            alertMsg.showAndWait();
            return TRUE;
        }

        return FALSE;
    }

    /**
     * Get all appointments in the specified timezone.
     * @param timezone Time zone the times should be in.
     * @return Observable list of appointments.
     */
    public static ObservableList<Appointment> getAppointmentsOL(String timezone, String filterView){
        ObservableList<Appointment> appointmentsOL = FXCollections.observableList(Datasource.queryAppointments());

        // Apply filter
        List<Appointment> allAppointments = Datasource.queryAppointments();
        List<Appointment> filteredAppointments = new ArrayList<>();
        Month currentMonth = LocalDateTime.now().getMonth();
        LocalDateTime currentEndOfWeek = LocalDateTime.now().with(DayOfWeek.SUNDAY);
        for(Appointment appointment : allAppointments){
            if(filterView == "Month Only"){
                Month startMonth = appointment.getStart().toLocalDateTime().getMonth();
                if(startMonth == currentMonth){
                    filteredAppointments.add(appointment);
                }
                continue;
            }

            if(filterView == "Week Only"){
                LocalDateTime startLdt = appointment.getStart().toLocalDateTime();
                if(startLdt.isAfter(LocalDateTime.now()) && startLdt.isBefore(currentEndOfWeek)){
                    filteredAppointments.add(appointment);
                }
                continue;
            }

            // Select all by default
            filteredAppointments = allAppointments;
        }

        // Update times to correct timezone
        for(Appointment appointment : filteredAppointments){
            // Calculate time zone times
            Timestamp updatedStart = TimeZones.convertUtcTime(appointment.getStart(),TimeZones.getZoneIdFromFormattedTimeZone(timezone));
            Timestamp updatedEnd = TimeZones.convertUtcTime(appointment.getEnd(),TimeZones.getZoneIdFromFormattedTimeZone(timezone));
            Timestamp updatedCreateDate = TimeZones.convertUtcTime(appointment.getCreateDate(),TimeZones.getZoneIdFromFormattedTimeZone(timezone));
            Timestamp updatedLastUpdate = TimeZones.convertUtcTime(appointment.getLastUpdate(),TimeZones.getZoneIdFromFormattedTimeZone(timezone));

            // Assign updated times
            appointment.setStart(updatedStart);
            appointment.setEnd(updatedEnd);
            appointment.setCreateDate(updatedCreateDate);
            appointment.setLastUpdate(updatedLastUpdate);
        }

        return FXCollections.observableList(filteredAppointments);
    }

    /**
     * Get all appointments.
     * @return List of appointments.
     */
    public static List<Appointment> getAppointments(){
        return Datasource.queryAppointments();
    }

    /**
     * Return a single appointment by user id
     * @param customerId
     * @return
     */
    public static Appointment getAppointment(int customerId){
        List<Appointment> appointments = getAppointments();
        for(Appointment appointment : appointments){
            if(appointment.getCustomerId() == customerId){
                return appointment;
            }
        }
        return null; // If no appointments are found.
    }

    /**
     * Return all appointment by user id
     * @param customerId
     * @return
     */
    public static List<Appointment> getUserAppointments(int customerId){
        List<Appointment> appointments = getAppointments();
        List<Appointment> userAppointments = new ArrayList<>();
        for(Appointment appointment : appointments){
            if(appointment.getCustomerId() == customerId){
                userAppointments.add(appointment);
            }
        }
        return userAppointments;
    }
}
