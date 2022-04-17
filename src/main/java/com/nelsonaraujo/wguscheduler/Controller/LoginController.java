package com.nelsonaraujo.wguscheduler.Controller;

import com.nelsonaraujo.wguscheduler.Model.*;
import com.nelsonaraujo.wguscheduler.wguScheduler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
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

    /**
     * Change the scene defaults.
     * @param url
     * @param rb Language resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serverNameLabel.setText(Datasource.SERVER_NAME); // Set server name
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

    /**
     * Process when the login button is clicked.
     * @throws IOException Operation fails
     */
    @FXML
    protected void onLoginButtonClick() throws IOException {
        Datasource datasource = new Datasource();

        if(usernameTextField.getText().trim().isEmpty() || passwordField.getText().isEmpty()) {
            loginErrorLabel.setText(Globals.rb_lang.getString("err_credentials_required"));
        } else if(!InetAddress.getByName(Datasource.SERVER_NAME).isReachable(1)){
            loginErrorLabel.setText(Globals.rb_lang.getString("err_server_unreachable"));
            Logger.logAction(Logger.ActionType.ERROR,"Server not reachable (" +Datasource.SERVER_NAME + ")");
        } else {
            // Logger setup
            Logger.setCurrUser(usernameTextField.getText().trim());
            Logger.setCurrServer(datasource.SERVER_NAME);

            if (Datasource.open(Logger.getDbUser(), Logger.getDbPass())) {
                // Verify user credentials
                if(Datasource.verifyUser(Logger.getCurrUser(), passwordField.getText().trim())){
                    Logger.logAction(Logger.ActionType.LOGIN, "User logged in");

                    // Open main scene
                    wguScheduler.mainScene();

                } else {
                    Logger.logAction(Logger.ActionType.ERROR, "Invalid credentials: " +Logger.getCurrUser());
                    loginErrorLabel.setText(Globals.rb_lang.getString("err_invalid_credentials"));
                    Datasource.close();
                }
            } else {
                Logger.logAction(Logger.ActionType.ERROR, "Unable to connect: " +Logger.getCurrUser());
                loginErrorLabel.setText(Globals.rb_lang.getString("err_unable_connect"));
                Datasource.close();
            }
        }
    }

    /**
     * Process when the close button is clicked.
     */
    @FXML
    protected void onCloseButtonClick(){
        Datasource.close();
        Platform.exit();
    }
}