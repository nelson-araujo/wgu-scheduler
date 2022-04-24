package com.nelsonaraujo.wguscheduler.Controller;

import com.nelsonaraujo.wguscheduler.Model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.Timestamp;
import java.util.regex.Pattern;

import static java.lang.Boolean.FALSE;

public class AppointmentAddController {
    @FXML Button cancelBtn;
    @FXML Button addBtn;
    @FXML ComboBox customerCmbBx;
    @FXML TextField typeTxtFld;
    @FXML TextField titleTxtFld;
    @FXML TextArea descriptionTxtArea;
    @FXML ComboBox locationCmbBx;
    @FXML ComboBox contactCmbBx;
    @FXML DatePicker dateDatePck;
    @FXML ComboBox timezoneCmbBx;
    @FXML TextField startTimeTxtFld;
    @FXML TextField endTimeTxtFld;

    /**
     * Initialize the scene.
     */
    public void initialize(){
        // Populate customers drop down
        customerCmbBx.setItems(FXCollections.observableArrayList(Customers.getCustomersNames()));

        // Populate locations drop down
        locationCmbBx.setItems(FXCollections.observableArrayList(Globals.locations));

        // Populate contacts drop down
        contactCmbBx.setItems(FXCollections.observableArrayList(Contacts.getContactNames()));

        // Populate timezone drop down and auto select the local timezone
        timezoneCmbBx.setItems(FXCollections.observableArrayList(TimeZones.getFormattedTimeZones()));
        timezoneCmbBx.setValue(TimeZones.getSystemTimeZoneFormatted());
    }

    /**
     * Actions to be taken when the add button is clicked.
     */
    @FXML
    private void addBtnAction(){
        if(isFieldsValid()){
            addAppointment();

            // Close stage
            Stage stage= (Stage) addBtn.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Add appointment to database.
     */
    @FXML
    private void addAppointment(){
        Integer customer = Customers.getCustomerId(customerCmbBx.getValue().toString());
        String type = typeTxtFld.getText();
        String title = titleTxtFld.getText();
        String description = descriptionTxtArea.getText();
        String location = locationCmbBx.getValue().toString();
        Integer contact = Contacts.getContactId(contactCmbBx.getValue().toString());
        String date = dateDatePck.getValue().toString();
        String timezone = timezoneCmbBx.getValue().toString();
        String start = startTimeTxtFld.getText();
        String end = endTimeTxtFld.getText();

        // Format start and end times
        String startDateTime = date +" " + start + ":00";
        String endDateTime = date +" " + end + ":00";
        // Convert start and end  times to Timestamps
        Timestamp startTs = Timestamp.valueOf(startDateTime);
        Timestamp endTs = Timestamp.valueOf(endDateTime);
        // Convert start and end times to local timestamps
        Timestamp startUtcTs = TimeZones.getUtcTime(startTs,timezone);
        Timestamp endUtcTs = TimeZones.getUtcTime(endTs,timezone);

        // Add appointment to database
        Datasource.addAppointment(title,description,location, type, startUtcTs,endUtcTs, customer,contact);
    }

    /**
     * Check if entered fields are valid.
     * @return true/false if fields are valid.
     */
    private Boolean isFieldsValid(){
        boolean isValid = true;
        String timeFormatRegex = "^([0-9]{1,2}:[0-9]{1,2})$"; // Time format regex
        String custName = customerCmbBx.getValue().toString();
        Timestamp aptStart = Timestamp.valueOf(
                dateDatePck.getValue().toString() +" " + startTimeTxtFld.getText().toString() + ":00");
        Timestamp aptEnd = Timestamp.valueOf(
                dateDatePck.getValue().toString() +" " + endTimeTxtFld.getText().toString() + ":00");
        String formattedTimeZone = timezoneCmbBx.getValue().toString();


        // todo: remove
        System.out.println("---");
//        System.out.println("Outside start: " + Appointments.isOutsideBusinessHours(aptStart,formattedTimeZone));
//        System.out.println("Outside End: " + Appointments.isOutsideBusinessHours(aptEnd,formattedTimeZone));
        System.out.println("Overlap: " + Appointments.isOverlapAppointment(custName,aptStart, aptEnd,formattedTimeZone));


        // Customer selection validation
        if(customerCmbBx.getValue() == null){
            invalidField(customerCmbBx);
            isValid = false;
        } else { resetField(customerCmbBx); }

        // Type selection validation
        if(typeTxtFld.getText().isEmpty()){
            invalidField(typeTxtFld);
            isValid = false;
        } else { resetField(typeTxtFld); }

        // Title selection validation
        if(titleTxtFld.getText().isEmpty()){
            invalidField(titleTxtFld);
            isValid = false;
        } else { resetField(titleTxtFld); }

        // Description selection validation
        if(descriptionTxtArea.getText().isEmpty()){
            invalidField(descriptionTxtArea);
            isValid = false;
        } else { resetField(descriptionTxtArea); }

        // Location selection validation
        if(locationCmbBx.getValue() == null){
            invalidField(locationCmbBx);
            isValid = false;
        } else { resetField(locationCmbBx); }

        // Contact selection validation
        if(contactCmbBx.getValue() == null){
            invalidField(contactCmbBx);
            isValid = false;
        } else { resetField(contactCmbBx); }

        // Date selection validation
        if(dateDatePck.getValue() == null || Appointments.isNotWeekend(aptStart, formattedTimeZone)){
            invalidField(dateDatePck);
            isValid = false;
        } else { resetField(dateDatePck); }

        // TimeZone selection validation
        if(timezoneCmbBx.getValue() == null){
            invalidField(timezoneCmbBx);
            isValid = false;
        } else { resetField(timezoneCmbBx); }

        // Start time selection validation
        if(startTimeTxtFld.getText().isEmpty() || !Pattern.matches(timeFormatRegex,startTimeTxtFld.getText())
                || Appointments.isOutsideBusinessHours(aptStart,formattedTimeZone)){
            invalidField(startTimeTxtFld);
            isValid = false;
        } else { resetField(startTimeTxtFld); }

        // Start time selection validation
        if(endTimeTxtFld.getText().isEmpty() || !Pattern.matches(timeFormatRegex,endTimeTxtFld.getText())
                || Appointments.isOutsideBusinessHours(aptEnd,formattedTimeZone)){
            invalidField(endTimeTxtFld);
            isValid = false;
        } else { resetField(endTimeTxtFld); }

        return isValid;
    }

    private static boolean isOutsideBusinessHouses(Timestamp time, String timezone){

        return FALSE; // todo: update
    }

    /**
     * Reset TextField invalid highlight.
     * @param field Field to be reset.
     */
    private static void resetField(TextField field){
        if(field != null){
            field.setStyle("-fx-border-color: #d3d3d3");
        }
    }

    /**
     * Reset DatePicker invalid highlight.
     * @param field Field to be reset.
     */
    private static void resetField(DatePicker field){
        if(field != null){
            field.setStyle("-fx-border-color: #d3d3d3");
        }
    }

    /**
     * Reset TextArea invalid highlight.
     * @param field Field to be reset.
     */
    private static void resetField(TextArea field){
        if(field != null){
            field.setStyle("-fx-border-color: #d3d3d3");
        }
    }

    /**
     * Reset ComboBox invalid highlight.
     * @param field Field to be reset.
     */
    private static void resetField(ComboBox field){
        if(field != null){
            field.setStyle("-fx-border-color: #d3d3d3");
        }
    }

    /**
     * Highlight invalid TextField.
     * @param field Field to highlight.
     */
    private static void invalidField(TextField field){
        if(field != null){
            field.setStyle("-fx-border-color: #d99999");
        }
    }

    /**
     * Highlight invalid DataPicker.
     * @param field Field to highlight.
     */
    private static void invalidField(DatePicker field){
        if(field != null){
            field.setStyle("-fx-border-color: #d99999");
        }
    }

    /**
     * Highlight invalid TextArea.
     * @param field Field to highlight.
     */
    private static void invalidField(TextArea field){
        if(field != null){
            field.setStyle("-fx-border-color: #d99999");
        }
    }

    /**
     * Highlight invalid ComboBox.
     * @param field Field to highlight.
     */
    private static void invalidField(ComboBox field){
        if(field != null){
            field.setStyle("-fx-border-color: #d99999");
        }
    }

    /**
     * Action to be taken when the cancel button is clicked
     */
    @FXML
    private void cancelBtnAction(){
        Stage stage= (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
