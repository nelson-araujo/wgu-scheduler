package com.nelsonaraujo.wguscheduler;

import com.nelsonaraujo.wguscheduler.Model.Datasource;
import com.nelsonaraujo.wguscheduler.Model.Logger;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class wguScheduler extends Application {
    static Stage stage;

    /**
     * Override defaults
     * @param stage Scheduler main stage
     * @throws IOException Fails to open
     */
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
        stage.setOnCloseRequest(e -> Datasource.close());
    }

    /**
     * Load the main scene, appointments.
     * @throws IOException Fails to open
     */
    @FXML
    public static void mainScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(wguScheduler.class.getResource("appointments-view.fxml"));
        Scene sceneLogin = new Scene(fxmlLoader.load());

        stage.setTitle("Scheduler" + " - " +Logger.getCurrUser() + "@" +Logger.getCurrServer());
        stage.setScene(sceneLogin);
        stage.show();

        // Set stage minimum size to the opening size.
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
    }

    /**
     * Load the customers scene.
     * @throws IOException Fails to open
     */
    @FXML
    public static void customersScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(wguScheduler.class.getResource("customers-view.fxml"));
        Scene sceneCustomers = new Scene(fxmlLoader.load());

        stage.setTitle("Scheduler" + " - " +Logger.getCurrUser() + "@" +Logger.getCurrServer());
        stage.setScene(sceneCustomers);
        stage.show();

        // Set stage minimum size to the opening size.
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
    }

    /**
     * Load the reports scene.
     * @throws IOException Fails to open
     */
    @FXML
    public static void reportsScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(wguScheduler.class.getResource("reports-view.fxml"));
        Scene sceneCustomers = new Scene(fxmlLoader.load());

        stage.setTitle("Scheduler" + " - " +Logger.getCurrUser() + "@" +Logger.getCurrServer());
        stage.setScene(sceneCustomers);
        stage.show();

        // Set stage minimum size to the opening size.
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
    }

    /**
     * Scheduler launch point.
     * @param args Arguments being passed
     */
    public static void main(String[] args) {
        launch();
    }
}