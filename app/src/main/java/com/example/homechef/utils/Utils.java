package com.example.homechef.utils;

import android.icu.text.DateIntervalFormat;

import java.util.regex.Pattern;

public class Utils {
    public static String timeToString(long timeInSeconds) {
        long hours = timeInSeconds / 3600;
        long minutes = (timeInSeconds % 3600) / 60;
        long seconds = timeInSeconds % 60;

        String timeString = "";

        if (hours > 0) {
            if (hours == 1) {
                timeString += "שעה אחת ";
            } else {
                timeString += hours + " שעות ";
            }

            if (minutes > 0) {
                timeString += "ו";
            }
        }

        if (minutes > 0) {
            if (minutes == 1) {
                timeString += "דקה אחת ";
            } else {
                timeString += minutes + " דקות ";
            }

            if (seconds > 0) {
                timeString += "ו";
            }
        }

        if (seconds > 0) {
            if (seconds == 1) {
                timeString += " שנייה אחת";
            } else {
                timeString += seconds + " שניות ";
            }
        }

        if (timeString.equals("")) {
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
