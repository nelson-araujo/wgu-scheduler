module com.nelsonaraujo.wguscheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.nelsonaraujo.wguscheduler to javafx.fxml;
    exports com.nelsonaraujo.wguscheduler;
}