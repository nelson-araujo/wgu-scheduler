package com.nelsonaraujo.wguscheduler.Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Logger {
    private static String dbUser = "sqluser";
    private static String dbUserPass = "passw0rd!";
    private static String currUser;
    private static String currServer;
    private static LocalDate date;
    private static String time;
    private static TimeZone currUserTimezone = TimeZone.getDefault();
    public enum ActionType {LOGIN, INFO, WARNING, ERROR}

    public static String getDbUser(){
        return dbUser;
    }

    public static String getDbPass(){
        return dbUserPass;
    }

    public static void logAction(ActionType actionType, String actionDescription){
        date = java.time.LocalDate.now(); // Get local date
        time = LocalDateTime.now().format(DateTimeFormatter.ofPattern(TimeZones.TIME_FORMAT)); // Get local current time

        // Create the log files
        if(createLogFile("login_activity") == FALSE || createLogFile("scheduler_log") == FALSE){
            System.out.println("[ERROR] Unable to create log files");
        } else {
            // Log login to login_activity
            if (actionType == ActionType.LOGIN) {
                try {
                    FileWriter writer = new FileWriter("login_activity.txt",true);
                    writer.write(
                            date
                            + "\t" + time
                            + "\t" + currUserTimezone.getDisplayName()
                            + "\t" + currUser
                            + "\t" + currServer
                            + "\t" + actionType
                            + "\t" + actionDescription
                            + "\n"
                    );
                    writer.close();
                } catch (IOException e){
                    System.out.println("ERROR - Unable to create login_activity.txt FileWriter");
                }
            }

            // Log action
            try {
                FileWriter writer = new FileWriter("scheduler_log.txt",true);
                writer.write(
                        date
                        + "\t" + time
                        + "\t" + currUserTimezone.getDisplayName()
                        + "\t" + currUser
                        + "\t" + currServer
                        + "\t" + actionType
                        + "\t" + actionDescription
                        + "\n"
                );
                writer.close();
            } catch (IOException e){
                System.out.println("[ERROR]" + e);
            }
        }
    }

    private static Boolean createLogFile(String filename) {
        File file = new File(filename +".txt");

        if (file.exists()) {
            return TRUE;
        } else {
            try {
                // Create file
                file.createNewFile();

                // Add headers
                try {
                    FileWriter writer = new FileWriter(filename +".txt", true);
                    writer.write(
                            "date"
                                    + "\t" + "time"
                                    + "\t" + "timezone"
                                    + "\t" + "user"
                                    + "\t" + "Server"
                                    + "\t" + "actionType"
                                    + "\t" + "actionDescription"
                                    + "\n"
                    );
                    writer.close();
                } catch (IOException e) {
                    System.out.println("[ERROR] " + e);
                }

                return TRUE;

            } catch (IOException e) {
                System.out.println("[ERROR] " + e);
                return FALSE;
            }
        }
    }

    public static String getCurrUser() {
        return currUser;
    }

    public static void setCurrUser(String currUser) {
        Logger.currUser = currUser;
    }

    public static String getCurrServer() {
        return currServer;
    }

    public static void setCurrServer(String currServer) {
        Logger.currServer = currServer;
    }

    public static TimeZone getCurrUserTimezone() {
        return currUserTimezone;
    }

}
