package com.nelsonaraujo.wguscheduler;

import com.nelsonaraujo.wguscheduler.Model.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class wguScheduler extends Application {

    static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(wguScheduler.class.getResource("login-view.fxml"));
        Scene sceneLogin = new Scene(fxmlLoader.load());

        stage.setTitle("Scheduler");
        stage.setScene(sceneLogin);
        stage.show();

        // Set stage minimum size to the opening size.
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());

        // Stage close request
        stage.setOnCloseRequest(e -> DBConnection.closeConnection());
    }

    @FXML
    public static void mainScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(wguScheduler.class.getResource("main-view.fxml"));
        Scene sceneLogin = new Scene(fxmlLoader.load());

        stage.setTitle("Scheduler - Main");
        stage.setScene(sceneLogin);
        stage.show();

        // Set stage minimum size to the opening size.
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());

    }

    public static void main(String[] args) {
        launch();
    }
}