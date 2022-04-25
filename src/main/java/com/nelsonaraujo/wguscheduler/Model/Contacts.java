package com.nelsonaraujo.wguscheduler.Model;

import java.util.ArrayList;
import java.util.List;


public class Contacts {
    /**
     * Get all contacts.
     * @return List of contacts.
     */
    public static List<Contact> getContacts(){
        return Datasource.queryContacts();
    }

    /**
     * Get all contact names. Lambda expression used for easy of readability and optimization of code.
     * @return List of contact names.
     */
    public static List<String> getContactNames(){
        List<String> contactNames = new ArrayList<>();

        getContacts().forEach(contact -> contactNames.add(contact.getName()));

        return contactNames;
    }

    /**
     * Get the ID of a contact by name.
     * @param name Name of contact.
     * @return ID of the contact.
     */
    public static Integer getContactId(String name){
        for(Contact contact : getContacts()){
            if(contact.getName().equals(name)){
                return contact.getId();
            }
        }

        return null;
    }
}
