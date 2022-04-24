package com.nelsonaraujo.wguscheduler.Controller;

import com.nelsonaraujo.wguscheduler.Model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class AppointmentUpdateController {
    static Appointment selectedAppointment;
    static String selectedTimeZone;
    @FXML TextField idTxtFld;
    @FXML Button cancelBtn;
    @FXML Button updateBtn;
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
     * Customer update controller
     * @param selectedAppointment
     */
    public AppointmentUpdateController(Appointment selectedAppointment, String selectedTimeZone){
        this.selectedAppointment = selectedAppointment;
        this.selectedTimeZone = selectedTimeZone;
    }

    /**
     * Class initializer, set values of the selected customer.
     */
    public void initialize(){
        // Populate customers drop down
        customerCmbBx.setItems(FXCollections.observableArrayList(Customers.getCustomersNames()));

        // Populate locations drop down
        locationCmbBx.setItems(FXCollections.observableArrayList(Globals.locations));

        // Populate contacts drop down
        contactCmbBx.setItems(FXCollections.observableArrayList(Contacts.getContactNames()));

        // Populate timezone drop down and auto select timezone
        timezoneCmbBx.setItems(FXCollections.observableArrayList(TimeZones.getFormattedTimeZones()));
        timezoneCmbBx.setValue(selectedTimeZone);

        // Set values
        idTxtFld.setText(Integer.toString(selectedAppointment.getId()));
        customerCmbBx.setValue(selectedAppointment.getCustomerName());
        typeTxtFld.setText(selectedAppointment.getType());
        titleTxtFld.setText(selectedAppointment.getTitle());
        descriptionTxtArea.setText(selectedAppointment.getDescription());
        locationCmbBx.setValue(selectedAppointment.getLocation());
        contactCmbBx.setValue(selectedAppointment.getContactName());
        dateDatePck.setValue(selectedAppointment.getStart().toLocalDateTime().toLocalDate());
        startTimeTxtFld.setText(selectedAppointment.getStart().toLocalDateTime().format(DateTimeFormatter.ofPattern(TimeZones.TIME_FORMAT)));
        endTimeTxtFld.setText(selectedAppointment.getEnd().toLocalDateTime().format(DateTimeFormatter.ofPattern(TimeZones.TIME_FORMAT)));
    }

    /**
     * Check if entered fields are valid.
     * @return true/false if fields are valid.
     */
    private Boolean isFieldsValid(){
        boolean isValid = true;
        String timeFormatRegex = "^([0-9]{1,2}:[0-9]{1,2})$"; // Time format regex

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
        if(dateDatePck.getValue() == null){
            invalidField(dateDatePck);
            isValid = false;
        } else { resetField(dateDatePck); }

        // TimeZone selection validation
        if(timezoneCmbBx.getValue() == null){
            invalidField(timezoneCmbBx);
            isValid = false;
        } else { resetField(timezoneCmbBx); }

        // Start time selection validation
        if(startTimeTxtFld.getText().isEmpty() || !Pattern.matches(timeFormatRegex,startTimeTxtFld.getText()) || isOutsideBusinessHours(dateDatePck.getValue().toString(), startTimeTxtFld.getText(),timezoneCmbBx.getValue().toString())){
            invalidField(startTimeTxtFld);
            isValid = false;
        } else { resetField(startTimeTxtFld); }

        // Start time selection validation
        if(endTimeTxtFld.getText().isEmpty() || !Pattern.matches(timeFormatRegex,endTimeTxtFld.getText()) || isOutsideBusinessHours(dateDatePck.getValue().toString(), endTimeTxtFld.getText(),timezoneCmbBx.getValue().toString())){
            invalidField(endTimeTxtFld);
            isValid = false;
        } else { resetField(endTimeTxtFld); }

        return isValid;
    }

    private static boolean isOutsideBusinessHours(String date, String time, String formattedTimeZone){
        String timeStrg = date +" " + time + ":00"; // Build the date time
        Timestamp dateTimeTs = Timestamp.valueOf(timeStrg); // Convert date and time to a timestamp

        // Create a ZonedDataTime with the provided time zone and convert it to UTC time.
        ZonedDateTime dateTimeZdt = dateTimeTs.toLocalDateTime().atZone(ZoneId.of(
                TimeZones.getZoneIdFromFormattedTimeZone(formattedTimeZone)));
        ZoneId zoneIdToConvertTo = ZoneId.of("US/Eastern"); // Convert zone id string to ZoneId
        ZonedDateTime utcDateTimeZdt = dateTimeZdt.toInstant().atZone(zoneIdToConvertTo);

        // Check is the date time falls in a weekend.
        DayOfWeek dateTimeDayOfWeek = utcDateTimeZdt.toLocalDateTime().toLocalDate().getDayOfWeek();
        if(dateTimeDayOfWeek == DayOfWeek.SATURDAY || dateTimeDayOfWeek == DayOfWeek.SUNDAY){
            Alert alertMsg = new Alert(Alert.AlertType.INFORMATION);
            alertMsg.setTitle("Outside business hours");
            alertMsg.setHeaderText("The Date you selected falls on the weekend.");
            alertMsg.showAndWait();

            return TRUE;
        }

        // Check if between 8:00 and 22:00 EST
        if(utcDateTimeZdt.getHour() < 8 || utcDateTimeZdt.getHour() >= 22){
            Alert alertMsg = new Alert(Alert.AlertType.INFORMATION);
            alertMsg.setTitle("Outside business hours");
            alertMsg.setHeaderText("The time you entered falls outside our 08:00 to 22:00 business hours.");
            alertMsg.showAndWait();
            return TRUE;
        }

        return FALSE;
    }

    /**
     * Actions to be taken when the update button is clicked.
     */
    @FXML
    private void updateBtnAction(){
        if(isFieldsValid()){
            updateAppointment();

            // Close stage
            Stage stage= (Stage) updateBtn.getScene().getWindow();
            stage.close();

        }
    }

    /**
     * Update appointment.
     */
    @FXML
    private void updateAppointment(){
        Integer customerId = Customers.getCustomerId(customerCmbBx.getValue().toString());
        String type = typeTxtFld.getText();
        String title = titleTxtFld.getText();
        String description = descriptionTxtArea.getText();
        String location = locationCmbBx.getValue().toString();
        Integer contactId = Contacts.getContactId(contactCmbBx.getValue().toString());
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
        Datasource.updateAppointment(selectedAppointment.getId(),title, description,
                                     location,type,startUtcTs,endUtcTs,customerId,contactId);
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
