module com.nelsonaraujo.wguscheduler {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.nelsonaraujo.wguscheduler to javafx.fxml;
    exports com.nelsonaraujo.wguscheduler;
}