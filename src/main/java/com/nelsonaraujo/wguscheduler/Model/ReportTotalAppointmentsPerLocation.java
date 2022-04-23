package com.nelsonaraujo.wguscheduler.Model;

public class ReportTotalAppointmentsPerLocation {
    String location;
    Integer appointmentsCount;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getAppointmentsCount() {
        return appointmentsCount;
    }

    public void setAppointmentsCount(Integer appointmentsCount) {
        this.appointmentsCount = appointmentsCount;
    }
}
