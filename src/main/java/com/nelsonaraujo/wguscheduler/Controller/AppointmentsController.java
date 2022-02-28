package com.nelsonaraujo.wguscheduler.Controller;

import com.nelsonaraujo.wguscheduler.Model.Appointment;
import com.nelsonaraujo.wguscheduler.Model.Appointments;
import com.nelsonaraujo.wguscheduler.Model.Datasource;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

public class AppointmentsController {
    @FXML TableView<Appointment> appointmentsTblView;
    @FXML ImageView appointmentsIcon;
    @FXML ImageView customersIcon;

    public void initialize(){
        appointmentsTblView.setItems(Appointments.getAppointmentsOL());
        Tooltip.install(appointmentsIcon, new Tooltip("Appointments"));
        Tooltip.install(customersIcon, new Tooltip("Customers"));

        // TODO: REMOVE
//        System.out.println("id:" + appointmentsTblView.getItems().get(0).getId()
//                +" | title:" + appointmentsTblView.getItems().get(0).getTitle()
//                +" | description:" + appointmentsTblView.getItems().get(0).getDescription()
//                +" | location:" + appointmentsTblView.getItems().get(0).getLocation()
//                +" | type:" + appointmentsTblView.getItems().get(0).getType()
//                +" | start:" + appointmentsTblView.getItems().get(0).getStart()
//                +" | end:" + appointmentsTblView.getItems().get(0).getEnd()
//                +" | createDate:" + appointmentsTblView.getItems().get(0).getCreateDate()
//                +" | createBy:" + appointmentsTblView.getItems().get(0).getCreateBy()
//                +" | lastUpdate:" + appointmentsTblView.getItems().get(0).getLastUpdate()
//                +" | lastUpdateBy:" + appointmentsTblView.getItems().get(0).getLastUpdateBy()
//                +" | customerId:" + appointmentsTblView.getItems().get(0).getCustomerId()
//                +" | userId:" + appointmentsTblView.getItems().get(0).getUserId()
//                +" | ContactId:" + appointmentsTblView.getItems().get(0).getContactId()
//                +" | customerName:" + appointmentsTblView.getItems().get(0).getCustomerName()
//        );
//        System.out.println("id:" + appointmentsTblView.getItems().get(1).getId()
//                +" | title:" + appointmentsTblView.getItems().get(1).getTitle()
//                +" | description:" + appointmentsTblView.getItems().get(1).getDescription()
//                +" | location:" + appointmentsTblView.getItems().get(1).getLocation()
//                +" | type:" + appointmentsTblView.getItems().get(1).getType()
//                +" | start:" + appointmentsTblView.getItems().get(1).getStart()
//                +" | end:" + appointmentsTblView.getItems().get(1).getEnd()
//                +" | createDate:" + appointmentsTblView.getItems().get(1).getCreateDate()
//                +" | createBy:" + appointmentsTblView.getItems().get(1).getCreateBy()
//                +" | lastUpdate:" + appointmentsTblView.getItems().get(1).getLastUpdate()
//                +" | lastUpdateBy:" + appointmentsTblView.getItems().get(1).getLastUpdateBy()
//                +" | customerId:" + appointmentsTblView.getItems().get(1).getCustomerId()
//                +" | userId:" + appointmentsTblView.getItems().get(1).getUserId()
//                +" | ContactId:" + appointmentsTblView.getItems().get(1).getContactId()
//                +" | customerName:" + appointmentsTblView.getItems().get(1).getCustomerName()
//        );
    }

    @FXML
    protected void onExitBtnClick(){
        Datasource.close();
        Platform.exit();
    }
}
