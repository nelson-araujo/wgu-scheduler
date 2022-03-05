package com.nelsonaraujo.wguscheduler.Controller;

import com.nelsonaraujo.wguscheduler.Model.Customers;
import com.nelsonaraujo.wguscheduler.Model.TimeZones;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

public class CustomerAddController {
    @FXML Button cancelBtn;
    @FXML TextField nameTxtFld;
    @FXML TextField phoneTxtFld;
    @FXML TextField addressTxtFld;
    @FXML TextField postalCodeTxtFld;
    @FXML ChoiceBox countryChcBx;
    @FXML ChoiceBox stateProvinceChcBx;

    /** Check if entered fields are valid.
     *
     * @return true/false if fields are valid.
     */
    private Boolean isFieldsValid(){
        boolean isValid = true;

        // Name text field validation
        if(nameTxtFld.getText().isEmpty()){
            invalidField(nameTxtFld);
            isValid = false;
        }

        // Phone text field validation
        if(phoneTxtFld.getText().isEmpty()){
            invalidField(phoneTxtFld);
            isValid = false;
        }
        
        // Address text field validation
        if(addressTxtFld.getText().isEmpty()){
            invalidField(addressTxtFld);
            isValid = false;
        }

        // Postal Code text field validation
        if(postalCodeTxtFld.getText().isEmpty()){
            invalidField(postalCodeTxtFld);
            isValid = false;
        }

        // Postal Code selection validation
        if(countryChcBx.getValue() == null){
            invalidField(countryChcBx);
            isValid = false;
        }

        // State/Province selection validation
        if(stateProvinceChcBx.getValue() == null){
            invalidField(stateProvinceChcBx);
            isValid = false;
        }

        return isValid;
    }

    // Highlight text field
    private static void invalidField(TextField field){
        if(field != null){
            field.setStyle("-fx-border-color: #d99999");
        }
    }

    // Highlight selection field
    private static void invalidField(ChoiceBox field){
        if(field != null){
            field.setStyle("-fx-border-color: #d99999");
        }
    }

    @FXML
    private void addBtnAction(){
        isFieldsValid();
    }

    @FXML
    private void cancelBtnAction(){
        Stage stage= (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
}
