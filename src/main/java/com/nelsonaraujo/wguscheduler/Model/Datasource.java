package com.nelsonaraujo.wguscheduler.Model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Datasource {
    // JDBC URL
    private static final String PROTOCOL = "jdbc";
    private static final String VENDOR_NAME = ":mysql:";
    public static final String SERVER_NAME = "192.168.56.101";
    private static final String DB_NAME = "client_schedule";
    private static final String CONNECTION_STRING = PROTOCOL + VENDOR_NAME + "//" + SERVER_NAME + "/" + DB_NAME; // Full JDBC URL

    // appointments table variables
    private static final String TABLE_APPOINTMENTS = "appointments";
    private static final String COLUMN_APPOINTMENT_ID = "Appointment_ID";
    private static final String COLUMN_APPOINTMENT_TYPE = "Type";
    private static final String COLUMN_APPOINTMENT_START = "Start";
    private static final String COLUMN_APPOINTMENT_LOCATION = "Location";
    private static final String COLUMN_APPOINTMENT_CUSTOMER_ID = "Customer_ID";

    // customers table variables
    private static final String TABLE_CUSTOMERS = "customers";
    private static final String COLUMN_CUSTOMER_ID = "Customer_ID";
    private static final String COLUMN_CUSTOMER_NAME = "Customer_Name";

    private static Connection conn;

    // Open the connection
    public static boolean open(String username, String password){
        try{
            conn = DriverManager.getConnection(CONNECTION_STRING, username, password);
            return true;

        } catch(SQLException e){
            Logger.logAction(Logger.ActionType.ERROR, e.getMessage()); // Log message

            // Notify user
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();

            return false;
        }
    }

    // Close the connection
    public static void close() {
        try {
            if (conn != null) {
                conn.close();
                Logger.logAction(Logger.ActionType.INFO, "Connection closed to " + CONNECTION_STRING);
            }
        } catch (SQLException e) {
            Logger.logAction(Logger.ActionType.ERROR, e.getMessage());
        }
    }

}