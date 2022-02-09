package com.nelsonaraujo.wguscheduler.Model;

import com.nelsonaraujo.wguscheduler.wguScheduler;
import javafx.scene.image.Image;
import java.util.Locale;

public class UserLocale {
    private static final Locale USER_LOCALE = Locale.getDefault(); // Set user locale to system default
//    private static final Locale USER_LOCALE = Locale.FRANCE; // Force user locale to France

    public static Locale getUserLocale() {
        return USER_LOCALE;
    }

    public static String getUserLocaleString() {
        return USER_LOCALE.toString();
    }

    public static Image getLocaleFlag(){
        if(USER_LOCALE == Locale.ENGLISH || USER_LOCALE == Locale.US){
            Image flag = new Image(wguScheduler.class.getResourceAsStream("LanguageFlags/unitedStatesFlag_25x17.png"));
            return flag;
        }
        if(USER_LOCALE == Locale.FRENCH || USER_LOCALE == Locale.FRANCE){
            Image flag = new Image(wguScheduler.class.getResourceAsStream("LanguageFlags/frenchFlag_25x17.png"));
            return flag;
        }
        
        return null;
    }
}
