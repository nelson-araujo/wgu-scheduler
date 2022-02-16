package com.nelsonaraujo.wguscheduler.Controller;

import com.nelsonaraujo.wguscheduler.Model.DBConnection;
import com.nelsonaraujo.wguscheduler.Model.Globals;
import com.nelsonaraujo.wguscheduler.Model.Logger;
import com.nelsonaraujo.wguscheduler.wguScheduler;
import com.nelsonaraujo.wguscheduler.Model.UserLocale;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    @FXML private Label titleLbl;
    @FXML private Label usernameLbl;
    @FXML private Label passwordLbl;
    @FXML private Label serverLbl;
    @FXML private Button loginBtn;
    @FXML private Button closeBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serverNameLabel.setText(DBConnection.SERVER_NAME); // Set server name
        userLocaleImage.setImage(UserLocale.getLocaleFlag());
        Tooltip.install(userLocaleImage, new Tooltip(UserLocale.getUserLocaleString())); // Create tooltip for language
        rb = Globals.rb_lang;

        // Update labels based on locale
        titleLbl.setText(rb.getString("title"));
        usernameLbl.setText(rb.getString("username"));
        passwordLbl.setText(rb.getString("password"));
        serverLbl.setText(rb.getString("server"));
        loginBtn.setText(rb.getString("btn_login"));
        closeBtn.setText(rb.getString("btn_close"));

    }

    @FXML
    protected void onLoginButtonClick() throws IOException {
        // TODO: Move DB error handling to DBConnection class
        if(usernameTextField.getText().trim().isEmpty() || passwordField.getText().isEmpty()) {
            loginErrorLabel.setText(Globals.rb_lang.getString("err_credentials_required"));
        } else if(!InetAddress.getByName(DBConnection.SERVER_NAME).isReachable(1)){
            loginErrorLabel.setText(Globals.rb_lang.getString("err_server_unreachable"));
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
                loginErrorLabel.setText(Globals.rb_lang.getString("err_unable_connect"));
            }
        }
    }

    @FXML
    protected void onCloseButtonClick(){
        DBConnection.closeConnection();
        Platform.exit();
    }
}