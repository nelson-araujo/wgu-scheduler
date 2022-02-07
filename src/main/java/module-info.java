module com.nelsonaraujo.wguscheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.nelsonaraujo.wguscheduler to javafx.fxml;
    exports com.nelsonaraujo.wguscheduler;
    exports com.nelsonaraujo.wguscheduler.Model;
    opens com.nelsonaraujo.wguscheduler.Model to javafx.fxml;
    exports com.nelsonaraujo.wguscheduler.Controller;
    opens com.nelsonaraujo.wguscheduler.Controller to javafx.fxml;
}