package com.nelsonaraujo.wguscheduler.Controller;

import com.nelsonaraujo.wguscheduler.Model.*;
import com.nelsonaraujo.wguscheduler.wguScheduler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;

public class CustomersController {
    @FXML ImageView appointmentsIcon;
    @FXML ImageView customersIcon;
    @FXML ImageView reportsIcon;
    @FXML TableView customersTblView;

    /**
     * Initialize the scene
     */
    public void initialize(){
        customersTblView.setItems(Customers.getCustomersOL());
        Tooltip.install(appointmentsIcon, new Tooltip("Appointments"));
        Tooltip.install(customersIcon, new Tooltip("Customers"));
        Tooltip.install(reportsIcon, new Tooltip("Reports"));
    }

    /**
     * Process when the appointments icon is clicked.
     * @throws IOException Operation fails
     */
    @FXML
    protected void onAppointmentsIconClick() throws IOException {
        wguScheduler.mainScene();
    }

    /**
     * Process when the reports icon is clicked.
     * @throws IOException Operation fails
     */
    @FXML
    protected void onReportsIconClick() throws IOException {
        wguScheduler.reportsScene();
    }

    /**
     * Process when the add button is clicked.
     * @param event Events from scene
     * @throws IOException Operation fails
     */
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

        // Refresh customer view
        customersTblView.setItems(Customers.getCustomersOL());
        customersTblView.refresh();
    }

    /**
     * Process when the update button is clicked.
     * @param event Events from scene
     * @throws IOException Operation fails
     */
    @FXML
    public void customerUpdateBtnOnAction(ActionEvent event) throws IOException{
        if(customersTblView.getSelectionModel().getSelectedItem() == null) {
            Alert alertMsg = new Alert(Alert.AlertType.INFORMATION);
            alertMsg.setTitle("No customer selected");
            alertMsg.setHeaderText("Select a customer to update");
            alertMsg.showAndWait();
        } else {
            Stage updateCustomerStage = new Stage();

            updateCustomerStage.initModality(Modality.APPLICATION_MODAL); // Stage must be closed to interact with main program
            FXMLLoader updateCustomerViewLoader = new FXMLLoader(getClass().getResource("/com/nelsonaraujo/wguscheduler/customer-update-view.fxml"));

            // Pass selected customer
            Customer selectedCustomer = (Customer) customersTblView.getSelectionModel().getSelectedItem();
            CustomerUpdateController customerUpdateController = new CustomerUpdateController(selectedCustomer);
            updateCustomerViewLoader.setController(customerUpdateController);

            // Open stage
            Parent root = updateCustomerViewLoader.load();
            Scene scene = new Scene(root);
            updateCustomerStage.setScene(scene);
            updateCustomerStage.setTitle("Update Customer");
            updateCustomerStage.setResizable(false);
            updateCustomerStage.showAndWait();

            // Refresh customer view
            customersTblView.setItems(Customers.getCustomersOL());
            customersTblView.refresh();
        }
    }

    /**
     * Process when the exit button is clicked
     */
    @FXML
    protected void onExitBtnClick(){
        Datasource.close();
        Platform.exit();
    }

    /**
     * Process when the delete button is clicked.
     */
    @FXML
    private void deleteBtnAction(){
        if(customersTblView.getSelectionModel().getSelectedItem() != null) {
            try {
                Customer selectedCustomer = (Customer) customersTblView.getSelectionModel().getSelectedItem();

                if (selectedCustomer.hasAppointment()) {
                    int previousAppointmentId = -1;

                    while (selectedCustomer.hasAppointment() && previousAppointmentId != selectedCustomer.getId()) {
                        Appointment appointment = Appointments.getAppointment(selectedCustomer.getId());

                        previousAppointmentId = appointment.getId();

                        if (!appointment.deleteAppointment()) {
                            break; // Exit if cancelled or fail.
                        }
                    }

                    // Delete customer
                    if (selectedCustomer.deleteCustomer()) {



                        customersTblView.setItems(Customers.getCustomersOL());
                        customersTblView.refresh();
                    }
                } else {
                    if (selectedCustomer.deleteCustomer()) {
                        // Notify the user the customer has been deleted
                        Alert alertMsg = new Alert(Alert.AlertType.INFORMATION);
                        alertMsg.setTitle("Customer deleted");
                        alertMsg.setHeaderText("customer " + selectedCustomer.getName() + "Has been deleted");
                        alertMsg.showAndWait();

                        customersTblView.setItems(Customers.getCustomersOL());
                        customersTblView.refresh();
                    }
                }
            } catch (Exception e) {
                Logger.logAction(Logger.ActionType.ERROR, e.getMessage());
            }
        } else {
            Alert alertMsg = new Alert(Alert.AlertType.INFORMATION);
            alertMsg.setTitle("No customer selected");
            alertMsg.setHeaderText("Select a customer to delete");
            alertMsg.showAndWait();
        }
    }
}
