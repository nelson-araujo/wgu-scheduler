package com.nelsonaraujo.wguscheduler.Model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    private static final String COLUMN_APPOINTMENT_TITLE = "Title";
    private static final String COLUMN_APPOINTMENT_DESCRIPTION = "Description";
    private static final String COLUMN_APPOINTMENT_LOCATION = "Location";
    private static final String COLUMN_APPOINTMENT_TYPE = "Type";
    private static final String COLUMN_APPOINTMENT_START = "Start";
    private static final String COLUMN_APPOINTMENT_END = "End";
    private static final String COLUMN_APPOINTMENT_CREATE_DATE = "Create_Date";
    private static final String COLUMN_APPOINTMENT_CREATE_BY = "Created_By";
    private static final String COLUMN_APPOINTMENT_LAST_UPDATE = "Last_Update";
    private static final String COLUMN_APPOINTMENT_LAST_UPDATE_BY = "Last_Updated_By";
    private static final String COLUMN_APPOINTMENT_CUSTOMER_ID = "Customer_ID";
    private static final String COLUMN_APPOINTMENT_USER_ID = "User_ID";
    private static final String COLUMN_APPOINTMENT_CONTACT_ID = "Contact_ID";

    // customer table variables
    private static final String TABLE_CUSTOMERS = "customers";
    private static final String COLUMN_CUSTOMER_ID = "Customer_ID";
    private static final String COLUMN_CUSTOMER_NAME = "Customer_Name";
    private static final String COLUMN_CUSTOMER_ADDRESS = "Address";
    private static final String COLUMN_CUSTOMER_POSTAL_CODE = "Postal_Code";
    private static final String COLUMN_CUSTOMER_PHONE = "Phone";
    private static final String COLUMN_CUSTOMER_CREATE_DATE = "Create_Date";
    private static final String COLUMN_CUSTOMER_CREATE_BY = "Created_By";
    private static final String COLUMN_CUSTOMER_LAST_UPDATE = "Last_Update";
    private static final String COLUMN_CUSTOMER_LAST_UPDATE_BY = "Last_Updated_By";
    private static final String COLUMN_CUSTOMER_DIVISION_ID = "Division_ID";

    // first_level_divisions table variables
    private static final String TABLE_FLD = "first_level_divisions";
    private static final String COLUMN_FLD_ID = "Division_ID";
    private static final String COLUMN_FLD_DIVISION = "Division";
    private static final String COLUMN_FLD_COUNTRY_ID = "Country_ID";

    // countries table variables
    private static final String TABLE_COUNTRIES = "countries";
    private static final String COLUMN_COUNTRY_ID = "Country_ID";
    private static final String COLUMN_COUNTRY_COUNTRY = "Country";

    // user table variables
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USERS_NAME = "User_Name";

    // contacts table variables
    private static final String TABLE_CONTACTS = "contacts";
    private static final String COLUMN_CONTACTS_NAME = "Contact_Name";

    // Database connection
    private static Connection conn;

    /**
     * Query database for all appointments.
     * @return List of all appointments.
     */
    public static List<Appointment> queryAppointments(){
        Statement statement = null;
        ResultSet results = null;

        // Query
        String query = "SELECT "
                            + TABLE_APPOINTMENTS + "." + COLUMN_APPOINTMENT_ID
                            + "," + TABLE_APPOINTMENTS + "." + COLUMN_APPOINTMENT_TITLE
                            + "," + TABLE_APPOINTMENTS + "." + COLUMN_APPOINTMENT_DESCRIPTION
                            + "," + TABLE_APPOINTMENTS + "." + COLUMN_APPOINTMENT_LOCATION
                            + "," + TABLE_APPOINTMENTS + "." + COLUMN_APPOINTMENT_TYPE
                            + "," + TABLE_APPOINTMENTS + "." + COLUMN_APPOINTMENT_START
                            + "," + TABLE_APPOINTMENTS + "." + COLUMN_APPOINTMENT_END
                            + "," + TABLE_APPOINTMENTS + "." + COLUMN_APPOINTMENT_CREATE_DATE
                            + "," + TABLE_APPOINTMENTS + "." + COLUMN_APPOINTMENT_CREATE_BY
                            + "," + TABLE_APPOINTMENTS + "." + COLUMN_APPOINTMENT_LAST_UPDATE
                            + "," + TABLE_APPOINTMENTS + "." + COLUMN_APPOINTMENT_LAST_UPDATE_BY
                            + "," + TABLE_APPOINTMENTS + "." + COLUMN_APPOINTMENT_CUSTOMER_ID
                            + "," + TABLE_APPOINTMENTS + "." + COLUMN_APPOINTMENT_USER_ID
                            + "," + TABLE_APPOINTMENTS + "." + COLUMN_APPOINTMENT_CONTACT_ID
                            + "," + TABLE_CUSTOMERS + "." + COLUMN_CUSTOMER_NAME
                        + " FROM " + TABLE_APPOINTMENTS
                        + " JOIN " + TABLE_CUSTOMERS
                            + " ON " + TABLE_APPOINTMENTS + "." + COLUMN_APPOINTMENT_CUSTOMER_ID
                            + " = " + TABLE_CUSTOMERS +"." + COLUMN_CUSTOMER_ID
        ;

        try{
            statement = conn.createStatement();
            results = statement.executeQuery(query);

            List<Appointment> appointments = new ArrayList<>();

            while(results.next()){
                Appointment appointment = new Appointment();

                appointment.setId(results.getInt(COLUMN_APPOINTMENT_ID));
                appointment.setTitle(results.getString(COLUMN_APPOINTMENT_TITLE));
                appointment.setDescription(results.getString(COLUMN_APPOINTMENT_DESCRIPTION));
                appointment.setLocation(results.getString(COLUMN_APPOINTMENT_LOCATION));
                appointment.setType(results.getString(COLUMN_APPOINTMENT_TYPE));
                appointment.setStart(results.getTimestamp(COLUMN_APPOINTMENT_START));
                appointment.setEnd((results.getTimestamp(COLUMN_APPOINTMENT_END)));
                appointment.setStart(results.getTimestamp(COLUMN_APPOINTMENT_CREATE_DATE));
                appointment.setCreateDate(results.getTimestamp(COLUMN_APPOINTMENT_CREATE_DATE));
                appointment.setCreateBy(results.getString(COLUMN_APPOINTMENT_CREATE_BY));
                appointment.setLastUpdate(results.getTimestamp(COLUMN_APPOINTMENT_LAST_UPDATE));
                appointment.setLastUpdateBy(results.getString(COLUMN_APPOINTMENT_LAST_UPDATE_BY));
                appointment.setCustomerId(results.getInt(COLUMN_APPOINTMENT_CUSTOMER_ID));
                appointment.setUserId(results.getInt(COLUMN_APPOINTMENT_USER_ID));
                appointment.setContactId(results.getInt(COLUMN_APPOINTMENT_CONTACT_ID));
                appointment.setCustomerName((results.getString(COLUMN_CUSTOMER_NAME)));

                appointments.add(appointment);
            }

            return appointments;

        } catch(SQLException e){
            System.out.println("Query Failed: " + e.getMessage());
            Logger.logAction(Logger.ActionType.ERROR, "Query failed: " + e.getMessage()); // Log message
            return null;
        } finally {
            // Close result set
            try{
                if(results != null){
                    results.close();
                }
            } catch(SQLException e){
                System.out.println("ResultSet failed to close" + e.getMessage());
                Logger.logAction(Logger.ActionType.ERROR, "ResultSet failed to close: " + e.getMessage()); // Log message
            }

            // Close statement
            try{
                if(statement != null) {
                    statement.close();
                }
            } catch(SQLException e){
                System.out.println("Statement failed to close" + e.getMessage());
                Logger.logAction(Logger.ActionType.ERROR, "Statement failed to close: " + e.getMessage()); // Log message
            }
        }
    }

    /**
     * Query database for all the customers.
     * @return List of all customers in the database.
     */
    public static List<Customer> queryCustomers(){
        Statement statement = null;
        ResultSet results = null;

        // Query
        String query = "SELECT "
                + TABLE_CUSTOMERS + "." + COLUMN_CUSTOMER_ID
                + "," + TABLE_CUSTOMERS + "." + COLUMN_CUSTOMER_NAME
                + "," + TABLE_CUSTOMERS + "." + COLUMN_CUSTOMER_ADDRESS
                + "," + TABLE_CUSTOMERS + "." + COLUMN_CUSTOMER_POSTAL_CODE
                + "," + TABLE_CUSTOMERS + "." + COLUMN_CUSTOMER_PHONE
                + "," + TABLE_CUSTOMERS + "." + COLUMN_CUSTOMER_CREATE_DATE
                + "," + TABLE_CUSTOMERS + "." + COLUMN_CUSTOMER_CREATE_BY
                + "," + TABLE_CUSTOMERS + "." + COLUMN_CUSTOMER_LAST_UPDATE
                + "," + TABLE_CUSTOMERS + "." + COLUMN_CUSTOMER_LAST_UPDATE_BY
                + "," + TABLE_CUSTOMERS + "." + COLUMN_CUSTOMER_DIVISION_ID
                + "," + TABLE_FLD + "." + COLUMN_FLD_DIVISION
                + "," + TABLE_COUNTRIES + "." + COLUMN_COUNTRY_COUNTRY
                + " FROM " + TABLE_CUSTOMERS
                + " JOIN " + TABLE_FLD
                    + " ON " + TABLE_CUSTOMERS + "." + COLUMN_CUSTOMER_DIVISION_ID
                    + " = " + TABLE_FLD +"." + COLUMN_FLD_ID
                + " JOIN " + TABLE_COUNTRIES
                    + " ON " + TABLE_FLD + "." + COLUMN_FLD_COUNTRY_ID
                    + " = " + TABLE_COUNTRIES +"." + COLUMN_COUNTRY_ID
        ;

        try{
            statement = conn.createStatement();
            results = statement.executeQuery(query);

            List<Customer> customers = new ArrayList<>();

            while(results.next()){
                Customer customer = new Customer();

                customer.setId(results.getInt(COLUMN_CUSTOMER_ID));
                customer.setName(results.getString(COLUMN_CUSTOMER_NAME));
                customer.setAddress(results.getString(COLUMN_CUSTOMER_ADDRESS));
                customer.setPostalCode(results.getString(COLUMN_CUSTOMER_POSTAL_CODE));
                customer.setPhone(results.getString(COLUMN_CUSTOMER_PHONE));
                customer.setCreateDate(results.getTimestamp(COLUMN_CUSTOMER_CREATE_DATE));
                customer.setCreateBy(results.getString(COLUMN_CUSTOMER_CREATE_BY));
                customer.setLastUpdate(results.getTimestamp(COLUMN_CUSTOMER_LAST_UPDATE));
                customer.setLastUpdateBy(results.getString(COLUMN_CUSTOMER_LAST_UPDATE_BY));
                customer.setDivisionId(results.getInt(COLUMN_CUSTOMER_DIVISION_ID));
                customer.setDivisionName(results.getString(COLUMN_FLD_DIVISION));
                customer.setCountryName(results.getString(COLUMN_COUNTRY_COUNTRY));

                customers.add(customer);
            }

            return customers;

        } catch(SQLException e){
            System.out.println("Query Failed: " + e.getMessage());
            Logger.logAction(Logger.ActionType.ERROR, "Query failed: " + e.getMessage()); // Log message
            return null;
        } finally {
            // Close result set
            try{
                if(results != null){
                    results.close();
                }
            } catch(SQLException e){
                System.out.println("ResultSet failed to close" + e.getMessage());
                Logger.logAction(Logger.ActionType.ERROR, "ResultSet failed to close: " + e.getMessage()); // Log message
            }

            // Close statement
            try{
                if(statement != null) {
                    statement.close();
                }
            } catch(SQLException e){
                System.out.println("Statement failed to close" + e.getMessage());
                Logger.logAction(Logger.ActionType.ERROR, "Statement failed to close: " + e.getMessage()); // Log message
            }
        }
    }

    /**
     * Query database for countries and their first level divisions.
     */
    public static void queryCountryDivisions2() {
        Statement statement = null;
        ResultSet results = null;

        // Query
        String query = "SELECT "
                + TABLE_COUNTRIES + "." + COLUMN_COUNTRY_COUNTRY
                + "," + TABLE_FLD + "." + COLUMN_FLD_DIVISION
                + "," + TABLE_FLD + "." + COLUMN_FLD_ID
                + " FROM " + TABLE_COUNTRIES
                + " JOIN " + TABLE_FLD
                + " ON " + TABLE_COUNTRIES + "." + COLUMN_COUNTRY_ID
                + " = " + TABLE_FLD +"." + COLUMN_FLD_COUNTRY_ID
                ;

        try{
            statement = conn.createStatement();
            results = statement.executeQuery(query);

            List<Country> countries = Countries.getCountries(); // Get countries list

            while(results.next()){
                FirstLevelDivisions firstLevelDivisionsList = new FirstLevelDivisions();

                Boolean countryExists = false;
                for(Country country : countries){
                    if(country.name.equals(results.getString(COLUMN_COUNTRY_COUNTRY))){
                        countryExists = true;
                    }

                    if(countryExists) break; // Break out of loop if exists
                }

                // Check if country already exists on list
                if(countryExists){
                    Country country = Countries.getCountry(results.getString(COLUMN_COUNTRY_COUNTRY)); // Set country name

                    firstLevelDivisionsList.setId(results.getInt(COLUMN_FLD_ID));
                    firstLevelDivisionsList.setName(results.getString(COLUMN_FLD_DIVISION));


                    country.setFirstLevelDivisions2(firstLevelDivisionsList); // Set first level divisions
                } else {
                    Country country = new Country();
                    country.setName(results.getString(COLUMN_COUNTRY_COUNTRY)); // Set country name

                    firstLevelDivisionsList.setId(results.getInt(COLUMN_FLD_ID));
                    firstLevelDivisionsList.setName(results.getString(COLUMN_FLD_DIVISION));
                    country.setFirstLevelDivisions2(firstLevelDivisionsList); // Set first level divisions

                    countries.add(country);
                }
            }
        } catch(SQLException e){
            System.out.println("Query Failed: " + e.getMessage());
            Logger.logAction(Logger.ActionType.ERROR, "Query failed: " + e.getMessage()); // Log message
        } finally {
            // Close result set
            try{
                if(results != null){
                    results.close();
                }
            } catch(SQLException e){
                System.out.println("ResultSet failed to close" + e.getMessage());
                Logger.logAction(Logger.ActionType.ERROR, "ResultSet failed to close: " + e.getMessage()); // Log message
            }

            // Close statement
            try{
                if(statement != null) {
                    statement.close();
                }
            } catch(SQLException e){
                System.out.println("Statement failed to close" + e.getMessage());
                Logger.logAction(Logger.ActionType.ERROR, "Statement failed to close: " + e.getMessage()); // Log message
            }
        }
    }

    /**
     * Open connection to the database.
     * @param username username to be used to connect.
     * @param password password to be used to connect.
     * @return Connection to the database.
     */
    public static boolean open(String username, String password){
        try{
            // TODO: username and password not the one on the client_schedule database
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

    /**
     * Add a customer to the database.
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param createBy
     * @param updateBy
     * @param divisionId
     * @return Add of customer successful.
     */
    public static boolean addCustomer(String name, String address, String postalCode,
                                      String phone, String createBy, String updateBy, Integer divisionId){
        String query = "INSERT INTO"
                + " " + TABLE_CUSTOMERS
                + " VALUES("
                + "NULL"
                + ",\"" + name + "\""
                + ",\"" + address + "\""
                + ",\"" + postalCode + "\""
                + ",\"" + phone + "\""
                + ",CURRENT_TIMESTAMP"
                + ",\"" + createBy + "\""
                + ",CURRENT_TIMESTAMP"
                + ",\"" + updateBy + "\""
                + "," + divisionId
                + ")"
                ;
        Logger.logAction(Logger.ActionType.INFO,"Add customer: " + name + ", " + phone );
        return runQueryNoResults(query);
    }

    /**
     * Delete a customer from the database.
     * @param id
     * @return
     */
    public static boolean deleteCustomer(int id){
        String query = "DELETE FROM"
                + " " + TABLE_CUSTOMERS
                + " WHERE"
                + " " + COLUMN_CUSTOMER_ID
                + "=" + id
                ;
        Customer customer = Customers.getCustomer(id); // Get customer
        Logger.logAction(Logger.ActionType.INFO,"Delete customer: \""
                + customer.getName() + " (" + customer.getId() +")\"" );
        return runQueryNoResults(query);
    }

    public static boolean deleteAppointment(int id){
        String query = "DELETE FROM"
                + " " + TABLE_APPOINTMENTS
                + " WHERE"
                + " " + COLUMN_APPOINTMENT_ID
                + "=" + id
                ;

        Appointment appointment = Appointments.getAppointment(id); // Get appointment

        Logger.logAction(Logger.ActionType.INFO,"Delete appointment " + id );

        return runQueryNoResults(query);
    }

    /**
     * Run a query with no result set.
     * @param query Full query to un.
     * @return Was query successful.
     */
    private static Boolean runQueryNoResults(String query){
        Statement statement = null;

        try{
            statement = conn.createStatement();
            statement.execute(query);
            return true;
        } catch(SQLException e){
            System.out.println("Query Failed: " + e.getMessage());
            Logger.logAction(Logger.ActionType.ERROR, "Query failed: " + e.getMessage()); // Log message
            return false;
        } finally {
            // Close statement
            try{
                if(statement != null) {
                    statement.close();
                }
            } catch(SQLException e){
                System.out.println("Statement failed to close" + e.getMessage());
                Logger.logAction(Logger.ActionType.ERROR, "Statement failed to close: " + e.getMessage()); // Log message
            }
        }
    }

    /**
     * Close the connection to the database.
     */
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