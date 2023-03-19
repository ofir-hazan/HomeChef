package com.example.homechef.utils;

import android.icu.text.DateIntervalFormat;

import java.util.regex.Pattern;

public class Utils {
    public static String timeToString(int timeInSeconds){
        // Convert int of duration in seconds to a string of format "X hours Y minutes Z seconds",
        // If 1 hour, return "an hour", if 1 minute, return "a minute", if 1 second, return "a second"
        // If 0 seconds, return "no time"

        int hours = timeInSeconds / 3600;
        int minutes = (timeInSeconds % 3600) / 60;
        int seconds = timeInSeconds % 60;

        String timeString = "";

        if (hours > 0){
            if (hours == 1){
                timeString += "שעה אחת ";
            } else {
                timeString += hours + " שעות ";
            }

            if(minutes > 0) {
                timeString += "ו";
            }
        }

        if (minutes > 0){
            if (minutes == 1){
                timeString += "דקה אחת ";
            } else {
                timeString += minutes + " דקות ";
            }

            if(seconds > 0) {
                timeString += "ו";
            }
        }

        if (seconds > 0){
            if (seconds == 1){
                timeString += " שנייה אחת";
            } else {
                timeString += seconds + " שניות ";
            }
        }

        if (timeString.equals("")){
            timeString = "בלי זמן";
        }

        return timeString;
    }

    public static boolean patternMatches(String emailAddress) {
        return Pattern.compile("^(.+)@(\\S+)$")
                .matcher(emailAddress)
                .matches();
    }
}
