package com.nelsonaraujo.wguscheduler.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Customers {

    /**
     * Get customers as an ObservableList.
     * @return
     */
    public static ObservableList<Customer> getCustomersOL(){
        ObservableList<Customer> customersOL = FXCollections.observableList(Datasource.queryCustomers());
        return customersOL;
    }

    /**
     * Get customers as a list.
     * @return
     */
    public static List<Customer> getCustomers(){
        List<Customer> customerList = Datasource.queryCustomers();
        return customerList;
    }

    /**
     * Find customer by id.
     * @param id
     * @return
     */
    public static Customer getCustomer(Integer id){
        List<Customer> customers = getCustomers();

        // Find customer
        for(Customer customer : customers){
            if(customer.getId() == id){
                return customer;
            }
        }
        return null;
    }
}
