package com.nelsonaraujo.wguscheduler.Model;

import java.util.ArrayList;
import java.util.List;

public class Country {
    String name;
    ArrayList<String> firstLevelDivisions = new ArrayList<String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFirstLevelDivisions() {
        return firstLevelDivisions;
    }

    public void setFirstLevelDivisions(String firstLevelDivision) {
        firstLevelDivisions.add(firstLevelDivision);
    }
}
