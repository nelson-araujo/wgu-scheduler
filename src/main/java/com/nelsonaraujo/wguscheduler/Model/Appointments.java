package com.nelsonaraujo.wguscheduler.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Appointments {
    public static ObservableList<Appointment> getAppointmentsOL(){
        ObservableList<Appointment> appointmentsOL = FXCollections.observableList(Datasource.queryAppointments());
        return appointmentsOL;
    }
}
