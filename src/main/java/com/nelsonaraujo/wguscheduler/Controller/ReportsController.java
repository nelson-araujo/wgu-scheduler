package com.nelsonaraujo.wguscheduler.Controller;

import com.nelsonaraujo.wguscheduler.Model.*;
import com.nelsonaraujo.wguscheduler.wguScheduler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ReportsController {
    @FXML ImageView appointmentsIcon;
    @FXML ImageView customersIcon;
    @FXML ImageView reportsIcon;
    @FXML ComboBox reportSelectionCmbBx;
    @FXML TableView reportTblVw;
    @FXML ComboBox contactCmbBx;
    @FXML Label contactCmbBxLbl;

    /**
     * Initialize the scene.
     */
    public void initialize(){
        Tooltip.install(appointmentsIcon, new Tooltip("Appointments"));
        Tooltip.install(customersIcon, new Tooltip("Customers"));
        Tooltip.install(reportsIcon, new Tooltip("Reports"));

        // Populate filter drop down
        reportSelectionCmbBx.setItems(getReportOptions()); // Populate options

        // Populate contacts drop down
        contactCmbBx.setItems(FXCollections.observableArrayList(Contacts.getContactNames()));
    }

    /**
     * Get the list of reports available.
     * @return list of report names.
     */
    private ObservableList<String> getReportOptions(){
        ArrayList<String> viewFilter = new ArrayList<>();
        viewFilter.add("Total appointments by type and month");
        viewFilter.add("Appointments by contact");
        viewFilter.add("Total appointments per office location");
        ObservableList<String> viewFilterOL = FXCollections.observableList(viewFilter);

        return viewFilterOL;
    }

    /**
     * Actions for when a report selection is made.
     */
    @FXML
    protected void onReportSelectionCmbBxAction(){
        String selectedReport = reportSelectionCmbBx.getValue().toString();

        if(selectedReport == "Total appointments by type and month"){
            reportTblVw.getItems().clear();
            reportTblVw.getColumns().clear();
            contactCmbBxLbl.setVisible(FALSE);
            contactCmbBx.setVisible(FALSE);

            // create and add the type column
            TableColumn<ReportTotalAppointments, String> colType = new TableColumn<>("Type");
            colType.setCellValueFactory(new PropertyValueFactory<>("type"));
            reportTblVw.getColumns().add(colType);

            // Create and add the month column
            TableColumn<ReportTotalAppointments, String> colMonth = new TableColumn<>("Month");
            colMonth.setCellValueFactory(new PropertyValueFactory<>("monthName"));
            reportTblVw.getColumns().add(colMonth);

            // Create and add the count column
            TableColumn<ReportTotalAppointments, String> colCount = new TableColumn<>("Total Appointments");
            colCount.setCellValueFactory(new PropertyValueFactory<>("monthCount"));
            reportTblVw.getColumns().add(colCount);


            reportTblVw.setItems(FXCollections.observableList(Reports.getReportTotalAppointments()));
        }

        if(selectedReport == "Appointments by contact"){
            reportTblVw.getItems().clear();
            reportTblVw.getColumns().clear();

            contactCmbBxLbl.setVisible(TRUE);
            contactCmbBx.setVisible(TRUE);
        }

        if(selectedReport == "Total appointments per office location"){
            reportTblVw.getItems().clear();
            reportTblVw.getColumns().clear();
            contactCmbBxLbl.setVisible(FALSE);
            contactCmbBx.setVisible(FALSE);

            // create and add the type column
            TableColumn<ReportTotalAppointmentsPerLocation, String> colLocation = new TableColumn<>("Location");
            colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
            reportTblVw.getColumns().add(colLocation);

            // Create and add the month column
            TableColumn<ReportTotalAppointmentsPerLocation, String> colAppointmentsCount = new TableColumn<>("Total Appointments");
            colAppointmentsCount.setCellValueFactory(new PropertyValueFactory<>("appointmentsCount"));
            reportTblVw.getColumns().add(colAppointmentsCount);

            reportTblVw.setItems(FXCollections.observableList(Reports.getReportTotalAppointmentsPerLocation()));
        };
    }

    /**
     * Select contact to run the appointments by contact report and run the report.
     */
    @FXML
    protected void onContactAction(){
        reportTblVw.getItems().clear();
        reportTblVw.getColumns().clear();

        Integer selectedContactId = Contacts.getContactId(contactCmbBx.getValue().toString());

        // create and add the appointment id column
        TableColumn<ReportContactAppointments, String> colAppointmentId = new TableColumn<>("Appointment ID");
        colAppointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        reportTblVw.getColumns().add(colAppointmentId);

        // Create and add the title column
        TableColumn<ReportContactAppointments, String> colTitle = new TableColumn<>("Title");
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        reportTblVw.getColumns().add(colTitle);

        // Create and add the type column
        TableColumn<ReportContactAppointments, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        reportTblVw.getColumns().add(colType);

        // Create and add the description column
        TableColumn<ReportContactAppointments, String> colDescription = new TableColumn<>("Description");
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        reportTblVw.getColumns().add(colDescription);

        // Create and add the start column
        TableColumn<ReportContactAppointments, String> colStart = new TableColumn<>("Start");
        colStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        reportTblVw.getColumns().add(colStart);

        // Create and add the end column
        TableColumn<ReportContactAppointments, String> colEnd = new TableColumn<>("End");
        colEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        reportTblVw.getColumns().add(colEnd);

        // Create and add the customer id column
        TableColumn<ReportContactAppointments, String> colCustomerId = new TableColumn<>("Customer ID");
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        reportTblVw.getColumns().add(colCustomerId);

        reportTblVw.setItems(FXCollections.observableList(Reports.getReportContactAppointments(selectedContactId)));
    }

    /**
     * Process when the appointments icon is clicked.
     * @throws IOException Operation fails
     */
    @FXML
    protected void onAppointmentsIconClick() throws IOException {
        wguScheduler.mainScene();
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
     * Process when the exit button is clicked.
     */
    @FXML
    protected void onExitBtnClick(){
        Datasource.close();
        Platform.exit();
    }
}
