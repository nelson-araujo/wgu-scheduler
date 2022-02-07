package com.nelsonaraujo.wguscheduler;

import com.nelsonaraujo.wguscheduler.Model.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Scheduler extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Scheduler.class.getResource("login-view.fxml"));
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