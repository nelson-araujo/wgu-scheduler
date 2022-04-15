package com.nelsonaraujo.wguscheduler.Controller;

import com.nelsonaraujo.wguscheduler.Model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class CustomerUpdateController {
    static Customer selectedCustomer;
    @FXML private Button updateBtn;
    @FXML private Button cancelBtn;
    @FXML private TextField idTxtFld;
    @FXML private TextField nameTxtFld;
    @FXML private TextField phoneTxtFld;
    @FXML private TextField addressTxtFld;
    @FXML private TextField postalCodeTxtFld;
    @FXML private ChoiceBox countryChcBx;
    @FXML private ChoiceBox stateProvinceChcBx;

    /**
     * Customer update controller
     * @param selectedCustomer
     */
    public CustomerUpdateController(Customer selectedCustomer){
        this.selectedCustomer = selectedCustomer;
    }

    /**
     * Class initializer, set values of the selected customer.
     */
    public void initialize(){
        // Populate Country list
        countryChcBx.setItems(FXCollections.observableArrayList(Countries.getCountriesList()));

        // Set values
        idTxtFld.setText(Integer.toString(selectedCustomer.getId()));
        nameTxtFld.setText(selectedCustomer.getName());
        phoneTxtFld.setText(selectedCustomer.getPhone());
        addressTxtFld.setText(selectedCustomer.getAddress());
        postalCodeTxtFld.setText(selectedCustomer.getPostalCode());
        countryChcBx.setValue(selectedCustomer.getCountryName());

        // Populate state/province list
        String countrySelection = countryChcBx.getValue().toString();
        stateProvinceChcBx.setItems((FXCollections.observableArrayList(Countries.getCountryFldNames(countrySelection))));

        stateProvinceChcBx.setValue(selectedCustomer.getDivisionName());
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
     * Actions to be taken when the update button is clicked.
     */
    @FXML
    private void updateBtnAction(){
        if(isFieldsValid()){
            updateCustomer();

            // Close stage
            Stage stage= (Stage) updateBtn.getScene().getWindow();
            stage.close();

        }
    }

    /**
     * Update customer.
     */
    @FXML
    private void updateCustomer(){
        Integer id = Integer.parseInt(idTxtFld.getText());
        String name = nameTxtFld.getText();
        String address = addressTxtFld.getText();
        String postalCode = postalCodeTxtFld.getText();
        String phone = phoneTxtFld.getText();
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

        Datasource.updateCustomer(id,name,address,postalCode,phone,updateBy,divisionId);
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
