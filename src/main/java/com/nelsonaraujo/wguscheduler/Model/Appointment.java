package com.nelsonaraujo.wguscheduler.Model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.sql.Timestamp;
import java.util.Optional;

public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private Timestamp createDate;
    private String createBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    private int customerId;
    private int userId;
    private int contactId;
    private String customerName;
    private String contactName;
    private String userName;

    /**
     * Ask user to confirm the deletion of an appointment.
     * @return
     */
    private Boolean confirmDelete(){
        Alert alertMsg = new Alert(Alert.AlertType.CONFIRMATION);
        alertMsg.setTitle("Delete: " + this.getTitle() + " (" + this.id +")");
        alertMsg.setHeaderText("Are you sure you want to delete " + this.getCustomerName()
                + " " + this.getTitle() + " (" + this.id +") appointment?");
        alertMsg.setContentText("Click OK to confirm");

        Optional<ButtonType> result = alertMsg.showAndWait();

        if(result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Confirm and delete appointment.
     * @return
     */
    public Boolean deleteAppointment(){
        if(confirmDelete()){
            return Datasource.deleteAppointment(this.getId());
        } else {
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
