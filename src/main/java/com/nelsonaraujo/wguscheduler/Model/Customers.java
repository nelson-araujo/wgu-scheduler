package com.nelsonaraujo.wguscheduler.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
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
     * Get customer names.
     * @return Customer names.
     */
    public static List<String> getCustomersNames(){
        List<Customer> customers = Datasource.queryCustomers();
        List<String> customerNames = new ArrayList<String>();

        for(Customer customer : customers){
            customerNames.add(customer.getName());
        }

        return customerNames;
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

    /**
     * Get customer ID from name.
     * @param name Customer name.
     * @return Customer ID.
     */
    public static Integer getCustomerId(String name){
        for(Customer customer : getCustomers()){
            if(customer.getName().equals(name)){
                return customer.getId();
            }
        }

        return null;
    }
}
