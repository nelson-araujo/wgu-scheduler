package com.nelsonaraujo.wguscheduler.Controller;

import com.nelsonaraujo.wguscheduler.Model.Appointment;
import com.nelsonaraujo.wguscheduler.Model.Appointments;
import com.nelsonaraujo.wguscheduler.Model.Countries;
import com.nelsonaraujo.wguscheduler.Model.Datasource;
import com.nelsonaraujo.wguscheduler.wguScheduler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class AppointmentsController {
    @FXML TableView<Appointment> appointmentsTblView;
    @FXML ImageView appointmentsIcon;
    @FXML ImageView customersIcon;

    public void initialize(){
        appointmentsTblView.setItems(Appointments.getAppointmentsOL());
        Tooltip.install(appointmentsIcon, new Tooltip("Appointments"));
        Tooltip.install(customersIcon, new Tooltip("Customers"));
    }

    @FXML
    protected void onCustomersIconClick() throws IOException {
        wguScheduler.customersScene();
    }

    @FXML
    protected void onExitBtnClick(){
        Datasource.close();
        Platform.exit();
    }
}
