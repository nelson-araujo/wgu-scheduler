package com.nelsonaraujo.wguscheduler.Controller;

import com.nelsonaraujo.wguscheduler.Model.Customers;
import com.nelsonaraujo.wguscheduler.Model.Datasource;
import com.nelsonaraujo.wguscheduler.wguScheduler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class ReportsController {
    @FXML ImageView appointmentsIcon;
    @FXML ImageView customersIcon;
    @FXML ImageView reportsIcon;

    public void initialize(){
        Tooltip.install(appointmentsIcon, new Tooltip("Appointments"));
        Tooltip.install(customersIcon, new Tooltip("Customers"));
        Tooltip.install(reportsIcon, new Tooltip("Reports"));
    }
    @FXML
    protected void onAppointmentsIconClick() throws IOException {
        wguScheduler.mainScene();
    }

    @FXML
    protected void onCustomersIconClick() throws IOException {
        wguScheduler.customersScene();
    }

    @FXML
    protected void onExitBtnClick(){
        Datasource.close();
        Platform.exit();
    }
}
