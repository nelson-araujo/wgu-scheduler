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
}
