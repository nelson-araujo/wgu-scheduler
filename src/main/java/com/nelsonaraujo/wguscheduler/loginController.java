package com.nelsonaraujo.wguscheduler;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class loginController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onLoginButtonClick() {
        System.out.println("Login button clicked");
    }

    @FXML
    protected void onCloseButtonClick(){
        System.out.println("Close button clicked");
    }
}