package com.nelsonaraujo.wguscheduler.Model;

import java.util.ArrayList;
import java.util.List;

public class Countries {
//    private static List<Country> countries = new ArrayList<>();
    private static List<Country> countries = new ArrayList<>();

    public static List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    /** Get country object that matches parameter.
     *
     * @param country
     * @return
     */
    public static Country getCountry(String country){
        if(countries.isEmpty()){
            return null;
        } else {
            for(int i=0 ; i < countries.size() ; i++){
                if(countries.get(i).getName().equals(country)){
                    return countries.get(i);
                }
            }
        }
        return null;
    }

    /** Get the list of all countries.
     * Extract all countries listed in the countries table of the database.
     * @return List of countries
     */
    public static List<String> getCountriesList(){
        if(countries.isEmpty()){
            Datasource.queryCountryDivisions2();
        }

        List<String> countryNames = new ArrayList<>();

        for(int i=0 ; i<countries.size() ; i++){
            countryNames.add(countries.get(i).getName());
        }

        return countryNames;
    }

    /** Get a country's first level divisions list.
     *
     * @param selectedCountry Country to return the first level divisions for.
     * @return List of first level division names and ID.
     */
    public static List<FirstLevelDivisions> getCountryFld(String selectedCountry){
        List<FirstLevelDivisions> fldList = new ArrayList<>();

        for(Country country : countries){
            if(country.getName() == selectedCountry){
                fldList = country.firstLevelDivisions2;
            }
        }

        return fldList;
    }

    /** Get a country's first level divisions.
     *
     * @param selectedCountry Country to return the first level divisions for.
     * @return List of first level divisions.
     */
    public static List<String> getCountryFldNames(String selectedCountry){
        List<String> fldList = new ArrayList<>();

        for(Country country : countries){
            if(country.getName() == selectedCountry){
                for( int i=0 ; i < country.getFirstLevelDivisions2().size() ; i++){
                    fldList.add(country.getFirstLevelDivisions2().get(i).name);
                }
            }
        }
        return fldList;
    }
}
