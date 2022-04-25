package com.nelsonaraujo.wguscheduler.Model;

import java.util.ArrayList;
import java.util.List;

public class Country {
    String name;
    ArrayList<String> firstLevelDivisions = new ArrayList<String>();
    List<FirstLevelDivisions> firstLevelDivisions2 = new ArrayList<FirstLevelDivisions>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FirstLevelDivisions> getFirstLevelDivisions2() {
        return firstLevelDivisions2;
    }

    public void setFirstLevelDivisions2(FirstLevelDivisions firstLevelDivision2) {
        firstLevelDivisions2.add(firstLevelDivision2);
    }
}
