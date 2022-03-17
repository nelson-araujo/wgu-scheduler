package com.nelsonaraujo.wguscheduler.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Appointments {
    /**
     * Get all appointments.
     * @return Observable list of appointments.
     */
    public static ObservableList<Appointment> getAppointmentsOL(){
        ObservableList<Appointment> appointmentsOL = FXCollections.observableList(Datasource.queryAppointments());
        return appointmentsOL;
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
