package com.nelsonaraujo.wguscheduler.Model;

import com.nelsonaraujo.wguscheduler.wguScheduler;
import javafx.scene.image.Image;
import java.util.Locale;

public class UserLocale {
    private static final Locale userLocale = Locale.getDefault(); // Set user locale to system default

    public static Locale getUserLocale() {
        return userLocale;
    }

    public static String getUserLocaleString() {
        return userLocale.toString();
    }

    public static Image getLocaleFlag(){
        if(userLocale == Locale.ENGLISH || userLocale == Locale.US){
            Image flag = new Image(wguScheduler.class.getResourceAsStream("languageFlags/unitedStatesFlag_25x17.png"));
            return flag;
        }
        if(userLocale == Locale.FRENCH || userLocale == Locale.FRANCE){
            Image flag = new Image(wguScheduler.class.getResourceAsStream("languageFlags/frenchFlag_25x17.png"));
            return flag;
        }
        
        return null;
    }
}
