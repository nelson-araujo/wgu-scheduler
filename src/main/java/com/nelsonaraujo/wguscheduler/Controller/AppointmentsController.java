package com.nelsonaraujo.wguscheduler.Controller;

import com.nelsonaraujo.wguscheduler.Model.Datasource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AppointmentsController {
    @FXML
    private Button exitBtn;

    public void initialize(){
//        Appointments.getAppointments();
    }

    @FXML
    protected void onexitBtnClick(){
        Datasource.close();
        Platform.exit();
    }

}
