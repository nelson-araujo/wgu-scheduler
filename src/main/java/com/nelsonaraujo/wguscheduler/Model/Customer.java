package com.nelsonaraujo.wguscheduler.Model;

import com.nelsonaraujo.wguscheduler.Controller.CustomersController;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Timestamp;
import java.util.Optional;

public class Customer {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private Timestamp createDate;
    private String createBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    private int divisionId;
    private String divisionName;
    private String countryName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Check if customer has an appointment scheduled
     * @return
     */
    public Boolean hasAppointment(){
        for(Appointment appointment : Appointments.getAppointments()) {
            if (appointment.getCustomerId() == this.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ask user to confirm the deletion of a customer.
     * @return
     */
    public Boolean confirmDelete(){
        Alert alertMsg = new Alert(Alert.AlertType.CONFIRMATION);
        alertMsg.setTitle("Delete: " + this.name + " (" + this.id +")");
        alertMsg.setHeaderText("Are you sure you want to delete \"" + this.name + " (" + this.id +")\"");
        alertMsg.setContentText("Click OK to confirm");

        Optional<ButtonType> result = alertMsg.showAndWait();

        if(result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Confirm and delete customer.
     * @return
     */
    public Boolean deleteCustomer(){
        if(confirmDelete()){
            return Datasource.deleteCustomer(this.getId());
        } else {
            return false;
        }
    }
}
