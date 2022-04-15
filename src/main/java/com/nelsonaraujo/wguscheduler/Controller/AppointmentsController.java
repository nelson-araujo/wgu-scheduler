package com.nelsonaraujo.wguscheduler.Controller;

import com.nelsonaraujo.wguscheduler.Model.*;
import com.nelsonaraujo.wguscheduler.wguScheduler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
     * Process when the exit button is clicked.
     */
    @FXML
    protected void onExitBtnClick(){
        Datasource.close();
        Platform.exit();
    }
}
