package com.nelsonaraujo.wguscheduler.Controller;

import com.nelsonaraujo.wguscheduler.Model.*;
import com.nelsonaraujo.wguscheduler.wguScheduler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class AppointmentsController {
    @FXML TableView<Appointment> appointmentsTblView;
    @FXML ImageView appointmentsIcon;
    @FXML ImageView customersIcon;
    @FXML ImageView reportsIcon;
    @FXML ChoiceBox timezoneChcBx;

    /**
     * Initialize the scene.
     */
    public void initialize(){
        appointmentsTblView.setItems(Appointments.getAppointmentsOL());
        Tooltip.install(appointmentsIcon, new Tooltip("Appointments"));
        Tooltip.install(customersIcon, new Tooltip("Customers"));
        Tooltip.install(reportsIcon, new Tooltip("Reports"));

        // Populate timezone drop down and auto select the local timezone
        timezoneChcBx.setItems(FXCollections.observableArrayList(TimeZones.getFormattedTimeZones()));
        timezoneChcBx.setValue(TimeZones.getSystemTimeZoneFormatted());



    }

    /**
     * Process when the customers icon is clicked.
     * @throws IOException Operation fails
     */
    @FXML
    protected void onCustomersIconClick() throws IOException {
        wguScheduler.customersScene();
    }

    /**
     * Process when the reports icon is clicked.
     * @throws IOException Operation fails
     */
    @FXML
    protected void onReportsIconClick() throws IOException {
        wguScheduler.reportsScene();
    }

    /**
     * Process when the exit button is clicked.
     */
    @FXML
    protected void onExitBtnClick(){
        Datasource.close();
        Platform.exit();
    }
}
