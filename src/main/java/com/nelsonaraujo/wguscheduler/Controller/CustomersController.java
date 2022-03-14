package com.nelsonaraujo.wguscheduler.Controller;

import com.nelsonaraujo.wguscheduler.Model.*;
import com.nelsonaraujo.wguscheduler.wguScheduler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class CustomersController {
    @FXML ImageView appointmentsIcon;
    @FXML ImageView customersIcon;
    @FXML TableView customersTblView;

    public void initialize(){
        customersTblView.setItems(Customers.getCustomersOL());
        Tooltip.install(appointmentsIcon, new Tooltip("Appointments"));
        Tooltip.install(customersIcon, new Tooltip("Customers"));
    }

    @FXML
    protected void onAppointmentsIconClick() throws IOException {
        wguScheduler.mainScene();
    }
    
    @FXML
    public void customerAddBtnOnAction(ActionEvent event) throws IOException{
        Stage addCustomerStage = new Stage();
        
        addCustomerStage.initModality(Modality.APPLICATION_MODAL); // Stage must be closed to interact with main program
        FXMLLoader addCustomerViewLoader = new FXMLLoader(getClass().getResource("/com/nelsonaraujo/wguscheduler/customer-add-view.fxml"));

        Parent root = addCustomerViewLoader.load();
        Scene scene = new Scene(root);
        addCustomerStage.setScene(scene);
        addCustomerStage.setTitle("Add Customer");
        addCustomerStage.setResizable(false);
        addCustomerStage.showAndWait();

        customersTblView.setItems(Customers.getCustomersOL());
        customersTblView.refresh();
    }

    @FXML
    public void customerUpdateBtnOnAction(ActionEvent event) throws IOException{
        Stage updateCustomerStage = new Stage();

        updateCustomerStage.initModality(Modality.APPLICATION_MODAL); // Stage must be closed to interact with main program
        FXMLLoader updateCustomerViewLoader = new FXMLLoader(getClass().getResource("/com/nelsonaraujo/wguscheduler/customer-update-view.fxml"));

        Parent root = updateCustomerViewLoader.load();
        Scene scene = new Scene(root);
        updateCustomerStage.setScene(scene);
        updateCustomerStage.setTitle("Update Customer");
        updateCustomerStage.setResizable(false);
        updateCustomerStage.showAndWait();
    }
    
    @FXML
    protected void onExitBtnClick(){
        Datasource.close();
        Platform.exit();
    }

    @FXML
    private void deleteBtnAction(){
        try{
            Customer selectedCustomer = (Customer) customersTblView.getSelectionModel().getSelectedItem();

            if(selectedCustomer.hasAppointment()){
                // todo: delete appointment first
                System.out.println("Delete appointment first"); // todo: remove
            } else {
                if(selectedCustomer.deleteCustomer()){
                    customersTblView.setItems(Customers.getCustomersOL());
                    customersTblView.refresh();
                }
            }
        } catch (Exception e) {
            Logger.logAction(Logger.ActionType.ERROR,e.getMessage());
        }
    }
}
