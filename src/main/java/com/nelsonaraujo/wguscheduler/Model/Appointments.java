package com.nelsonaraujo.wguscheduler.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Timestamp;
import java.time.*;
import java.util.*;

public class Appointments {
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
     * Return an appointment by user id
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
}
