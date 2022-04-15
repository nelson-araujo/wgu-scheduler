package com.nelsonaraujo.wguscheduler.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AppointmentAddController {
    @FXML
    Button cancelBtn;
    @FXML Button addBtn;

    /**
     * Actions to be taken when the add button is clicked.
     */
    @FXML
    private void addBtnAction(){
        // todo: create
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
