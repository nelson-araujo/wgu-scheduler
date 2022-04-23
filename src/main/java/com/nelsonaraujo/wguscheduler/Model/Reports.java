package com.nelsonaraujo.wguscheduler.Model;

import java.util.ArrayList;
import java.util.List;

public class Reports {
    /**
     * Get the total appointments report.
     * @return total appointments report
     */
    public static List<ReportTotalAppointments> getReportTotalAppointments(){
        List<ReportTotalAppointments> report = new ArrayList<>();

        for(ReportTotalAppointments reportLine : Datasource.queryReportTotalAppointments()){
            report.add(reportLine);
        }

        return report;
    }

    /**
     * Get a contact appointments report.
     * @param selectedContactId Contact to run report on.
     * @return Contact appointments report.
     */
    public static List<ReportContactAppointments> getReportContactAppointments(Integer selectedContactId){
        List<ReportContactAppointments> report = new ArrayList<>();

        for(ReportContactAppointments reportLine : Datasource.queryReportContactAppointments(selectedContactId)){
            report.add(reportLine);
        }

        return report;
    }

    /**
     * Get Total appointments per office locations.
     * @return Appointment totals per office locations.
     */
    public static List<ReportTotalAppointmentsPerLocation> getReportTotalAppointmentsPerLocation(){
        List<ReportTotalAppointmentsPerLocation> report = new ArrayList<>();

        for(ReportTotalAppointmentsPerLocation reportLine : Datasource.queryReportTotalAppointmentsPerLocation()){
            report.add(reportLine);
        }

        return report;
    }
}
