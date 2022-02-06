package com.nelsonaraujo.wguscheduler;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class loginApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(loginApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Scheduler");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> DBConnection.closeConnection());
    }

    public static void main(String[] args) {
        launch();
    }
}