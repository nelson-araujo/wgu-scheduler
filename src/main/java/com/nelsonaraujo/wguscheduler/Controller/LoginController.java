package com.nelsonaraujo.wguscheduler.Controller;

import com.nelsonaraujo.wguscheduler.Model.DBConnection;
import com.nelsonaraujo.wguscheduler.Model.Logger;
import com.nelsonaraujo.wguscheduler.wguScheduler;
import com.nelsonaraujo.wguscheduler.Model.UserLocale;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;
    @FXML private Label serverNameLabel;
    @FXML public Label loginErrorLabel;
    @FXML private ImageView userLocaleImage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serverNameLabel.setText(DBConnection.SERVER_NAME); // Set server name
        userLocaleImage.setImage(UserLocale.getLocaleFlag());
            Tooltip.install(userLocaleImage, new Tooltip(UserLocale.getUserLocaleString()));
    }

    @FXML
    protected void onLoginButtonClick() throws IOException {
        // TODO: Move DB error handling to DBConnection class
        if(usernameTextField.getText().trim().isEmpty() || passwordField.getText().isEmpty()) {
            loginErrorLabel.setText("A Username and password is required");
        } else if(!InetAddress.getByName(DBConnection.SERVER_NAME).isReachable(1)){
            loginErrorLabel.setText("Server not reachable");
            Logger.logAction(Logger.ActionType.ERROR,"Server not reachable (" +DBConnection.SERVER_NAME + ")");
        } else {
            // Logger setup
            Logger.setCurrUser(usernameTextField.getText().trim());
            Logger.setCurrServer(DBConnection.SERVER_NAME);

            if (DBConnection.startConnection(usernameTextField.getText().trim(), passwordField.getText().trim()) != null) {
                Logger.logAction(Logger.ActionType.LOGIN, "User logged in");

                // Open main scene
                wguScheduler.mainScene();
            } else {
                loginErrorLabel.setText("Unable to connect, please try again");
            }
        }
    }

    @FXML
    protected void onCloseButtonClick(){
        DBConnection.closeConnection();
        Platform.exit();
    }
}