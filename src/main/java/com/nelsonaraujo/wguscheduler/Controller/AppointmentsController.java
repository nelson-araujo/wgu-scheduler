package com.nelsonaraujo.wguscheduler.Controller;

import com.nelsonaraujo.wguscheduler.Model.*;
import com.nelsonaraujo.wguscheduler.wguScheduler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AppointmentsController {
    @FXML TableView<Appointment> appointmentsTblView;
    @FXML TableView<Appointment> upcomingAppointmentsTblView;
    @FXML ImageView appointmentsIcon;
    @FXML ImageView customersIcon;
    @FXML ImageView reportsIcon;
    @FXML ChoiceBox timezoneChcBx;

    /**
     * Initialize the scene.
     */
    public void initialize(){
        // Create and apply tooltips
        Tooltip.install(appointmentsIcon, new Tooltip("Appointments"));
        Tooltip.install(customersIcon, new Tooltip("Customers"));
        Tooltip.install(reportsIcon, new Tooltip("Reports"));

        // Populate timezone drop down and auto select the local timezone
        timezoneChcBx.setItems(FXCollections.observableArrayList(TimeZones.getFormattedTimeZones()));
        timezoneChcBx.setValue(TimeZones.getSystemTimeZoneFormatted());

        // Get appointments
        String selectedTimeZone = timezoneChcBx.getValue().toString();
        ObservableList<Appointment> appointmentsOL= Appointments.getAppointmentsOL(selectedTimeZone);

        // Populate tables
        upcomingAppointmentsTblView.setItems(getUpcomingAppointments(appointmentsOL));
        appointmentsTblView.setItems(appointmentsOL);
    }

    /**
     * Return the upcoming appointments, within the next 15 minutes.
     * @param allAppointments List of all appointments.
     * @return List of upcoming appointments, within the next 15 minutes.
     */
    private ObservableList<Appointment> getUpcomingAppointments(ObservableList<Appointment> allAppointments){
        ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();

        for(Appointment appointment : allAppointments){
            Instant startTimeMax = Instant.now().plusSeconds(TimeUnit.MINUTES.toSeconds(15));

            // Add to list if within 15 minutes
            if(appointment.getStart().toInstant().isBefore(startTimeMax)
                    && appointment.getStart().toInstant().isAfter(Instant.now())){

                upcomingAppointments.add(appointment);
            }
        }

        return upcomingAppointments;
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
     * Process when the add button is clicked.
     * @param event
     * @throws IOException
     */
    @FXML
    public void appointmentAddBtnOnAction(ActionEvent event) throws IOException{
        Stage addCustomerStage = new Stage();

        addCustomerStage.initModality(Modality.APPLICATION_MODAL); // Stage must be closed to interact with main program
        FXMLLoader addCustomerViewLoader = new FXMLLoader(getClass().getResource("/com/nelsonaraujo/wguscheduler/appointment-add-view.fxml"));

        Parent root = addCustomerViewLoader.load();
        Scene scene = new Scene(root);
        addCustomerStage.setScene(scene);
        addCustomerStage.setTitle("Add Appointment");
        addCustomerStage.setResizable(false);
        addCustomerStage.showAndWait();

        // todo: update
        // Refresh appointments view
//        customersTblView.setItems(Customers.getCustomersOL());
//        customersTblView.refresh();
    }

    /**
     * Action to be taken when the time zone selection is changed.
     */
    @FXML
    private void onTimeZoneChcBxAction(){
        String selectedTimeZone = timezoneChcBx.getValue().toString();
        ObservableList<Appointment> allAppointments = Appointments.getAppointmentsOL(selectedTimeZone);

        appointmentsTblView.setItems(allAppointments);
        upcomingAppointmentsTblView.setItems(getUpcomingAppointments(allAppointments));

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
