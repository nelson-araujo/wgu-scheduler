package com.nelsonaraujo.wguscheduler.Controller;

import com.nelsonaraujo.wguscheduler.Model.Appointment;
import com.nelsonaraujo.wguscheduler.Model.Appointments;
import com.nelsonaraujo.wguscheduler.Model.Datasource;
import com.nelsonaraujo.wguscheduler.wguScheduler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class CustomersController {
//    @FXML TableView<Customers> customersTblView;
    @FXML ImageView appointmentsIcon;
    @FXML ImageView customersIcon;

    public void initialize(){
//        customersTblView.setItems(Customers.getCustomersOL());
        Tooltip.install(appointmentsIcon, new Tooltip("Appointments"));
        Tooltip.install(customersIcon, new Tooltip("Customers"));
    }

    @FXML
    protected void onAppointmentsIconClick() throws IOException {
        wguScheduler.mainScene();
    }

    @FXML
    protected void onExitBtnClick(){
        Datasource.close();
        Platform.exit();
    }
}
