package com.nelsonaraujo.wguscheduler.Model;

import java.util.List;

public class Users {
    /**
     * Get all users.
     * @return List of all users.
     */
    public static List<User> getUsers(){
        return Datasource.queryUsers();
    }

    /**
     * Get a user ID from the username.
     * @param username Username.
     * @return User id.
     */
    public static Integer getUserId(String username){
        for(User user : getUsers()){
            if(user.getUsername().equals(username)){
                return user.getId();
            }
        }

        return null;
    }
}
