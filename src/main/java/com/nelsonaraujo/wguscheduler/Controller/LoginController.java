package com.nelsonaraujo.wguscheduler.Controller;

import com.nelsonaraujo.wguscheduler.Model.DBConnection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;
    @FXML private Label serverNameLabel;
    @FXML public Label loginErrorLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serverNameLabel.setText(DBConnection.SERVER_NAME);
    }


    @FXML
    protected void onLoginButtonClick() {
        if(usernameTextField.getText().trim().isEmpty() || passwordField.getText().isEmpty()){
            loginErrorLabel.setText("A Username and password is required");
        } else {
            DBConnection.startConnection(usernameTextField.getText().trim(), passwordField.getText().trim());
        }

    }

    @FXML
    protected void onCloseButtonClick(){
        DBConnection.closeConnection();
        Platform.exit();
    }
}