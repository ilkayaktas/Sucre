package edu.metu.sucre.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by iaktas on 05.06.2017.
 */

public class DateUtils {
    private DateUtils() {
        // This utility class is not publicly instantiable
    }

    public final static DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
    public final static DateFormat hourFormat = new SimpleDateFormat("HH:mm");
    public final static DateFormat messageFormat = new SimpleDateFormat("MMM d, EEE 'at' h:mm a", Locale.getDefault());

    public static String getFormattedDate(Date date){
        return dateFormat.format(date);
    }
    
    public static String getFormattedDateAsHour(Date date){
        return hourFormat.format(date);
    }

    public static String formatDateForMessaging(Date date){
        return messageFormat.format(date);
    }

    public static Date parseDateForMessaging(String date){
        try {
            return messageFormat.parse(date);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
    
}
