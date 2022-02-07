package com.nelsonaraujo.wguscheduler.Model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // JDBC URL
    private static final String PROTOCOL = "jdbc";
    private static final String VENDOR_NAME = ":mysql:";
    public static final String SERVER_NAME = "192.168.56.101";
    private static final String JDBC_URL = PROTOCOL + VENDOR_NAME +"//" + SERVER_NAME; // Full JDBC URL


    // Driver and connection interface references
    private static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection connectionToDb = null;

    // Open the connection
    public static Connection startConnection(String username, String password){
        try{
            Class.forName(MYSQL_JDBC_DRIVER); // Retrieve the driver
            connectionToDb = DriverManager.getConnection(JDBC_URL, username, password);
            System.out.println("[INFO] Connection established: " + JDBC_URL);

        } catch(ClassNotFoundException | SQLException e){
            System.out.print("[ERROR] " + e.getMessage() + "\n");

            // Notify the user of the error
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();
        }
        return connectionToDb;

    }

    // Close the connection
    public static void closeConnection(){
        try {
            connectionToDb.close();
            System.out.println("[INFO] Connection closed: " + JDBC_URL);
        } catch (SQLException e){
            System.out.println("[ERROR] " + e.getMessage() +"\n");
        } catch (NullPointerException e){
            System.out.println("[INFO] No connections to close");
        }
    }

}
