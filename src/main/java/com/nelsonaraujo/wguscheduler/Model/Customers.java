package com.nelsonaraujo.wguscheduler.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customers {
    public static ObservableList<Customer> getCustomersOL(){
        ObservableList<Customer> customersOL = FXCollections.observableList(Datasource.queryCustomers());
        return customersOL;
    }
}
