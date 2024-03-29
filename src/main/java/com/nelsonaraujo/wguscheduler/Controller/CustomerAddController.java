package com.nelsonaraujo.wguscheduler.Controller;

import com.nelsonaraujo.wguscheduler.Model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.List;

public class CustomerAddController {
    @FXML Button cancelBtn;
    @FXML Button addBtn;
    @FXML TextField nameTxtFld;
    @FXML TextField phoneTxtFld;
    @FXML TextField addressTxtFld;
    @FXML TextField postalCodeTxtFld;
    @FXML ChoiceBox countryChcBx;
    @FXML ChoiceBox stateProvinceChcBx;

    /**
     * Initialize the scene.
     */
    public void initialize(){
        countryChcBx.setItems(FXCollections.observableArrayList(Countries.getCountriesList()));
    }

    /**
     * Check if entered fields are valid.
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

    /**
     * Highlight specified field.
     * @param field Field that is not valid
     */
    private static void invalidField(TextField field){
        if(field != null){
            field.setStyle("-fx-border-color: #d99999");
        }
    }

    /**
     * Check if user inputs are valid
     * @param field Are fields valid?
     */
    private static void invalidField(ChoiceBox field){
        if(field != null){
            field.setStyle("-fx-border-color: #d99999");
        }
    }

    /**
     * Actions to be taken when the add button is clicked.
     */
    @FXML
    private void addBtnAction(){
        if(isFieldsValid()){
            addCustomer();

            // Close stage
            Stage stage= (Stage) addBtn.getScene().getWindow();
            stage.close();
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

    /**
     * When the country is selected populate the first division selection.
     */
    @FXML
    private void onCountrySelection(){
        String countrySelection = countryChcBx.getValue().toString();
        stateProvinceChcBx.setItems((FXCollections.observableArrayList(Countries.getCountryFldNames(countrySelection))));
    }

    /**
     * Add a customer.
     */
    @FXML
    private void addCustomer(){
        String name = nameTxtFld.getText();
        String address = addressTxtFld.getText();
        String postalCode = postalCodeTxtFld.getText();
        String phone = phoneTxtFld.getText();
        String createBy = Logger.getCurrUser();
        String updateBy = Logger.getCurrUser();
        Integer divisionId = null ;
        String countrySelected = countryChcBx.getValue().toString();
        String divisionSelected = stateProvinceChcBx.getValue().toString();

        // Find the first level division ID
        List<FirstLevelDivisions> countryFldList = Countries.getCountryFld(countrySelected);
        for(FirstLevelDivisions fld : countryFldList){
            if(fld.getName() == divisionSelected){
                divisionId = fld.getId();
                break; // Exit out of loop
            }
        }

        Datasource.addCustomer(name,address,postalCode,phone,createBy,updateBy,divisionId);
    }
}
