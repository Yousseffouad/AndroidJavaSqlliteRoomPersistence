package com.xavier.sqliteexample.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    public static String getCurrentTime(){
        try {
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            String currentDateTime =  dateFormat.format(new Date());
            return  currentDateTime;
        }catch (Exception e){
            return null;
        }
    }
    public static String getNumberOfMonths(String numberMonth){
        switch (numberMonth){
            case "01":
                return "Jan";

            case "02":
                return "Feb";

            case "03":
                return "Mar";

            case "04":
                return "Apr";

            case "05":
                return "May";

            case "06":
                return "June";

            case "07":
                return "July";

            case "08":
                return "Aug";

            case "09":
                return "Sept";

            case "10":
                return "Oct";

            case "11":
                return "Nov";

            case "12":
                return "Dec";

            default: {
                return "Error";
            }
        }


    }

}
