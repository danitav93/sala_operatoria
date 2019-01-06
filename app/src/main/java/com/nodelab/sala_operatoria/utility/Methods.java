package com.nodelab.sala_operatoria.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Methods {

    public static String getNextdate(String lastDate) {

        SimpleDateFormat format = new SimpleDateFormat("EEEE, dd/MM/yyyy", Locale.ITALY);
        try {
            //  String s=format.format(c);
            Date date = format.parse(lastDate);
            date = addDays(date, 1);
            String str = format.format(date);
            return str.substring(0, 1).toUpperCase() + str.substring(1);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getHour() {

        Calendar cal = Calendar.getInstance();
        cal.setTime(cal.getTime());
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        return hours;
    }

    public static String getTodayDate() {
        SimpleDateFormat format = new SimpleDateFormat("EEEE, dd/MM/yyyy", Locale.ITALY);
        Date c = Calendar.getInstance().getTime();
        String str = format.format(c);
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }


    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }



    public static String getYesterdayDate() {
        SimpleDateFormat format = new SimpleDateFormat("EEEE, dd/MM/yyyy", Locale.ITALY);
        try {
            //  String s=format.format(c);
            Date date = format.parse(getTodayDate());
            date = addDays(date, -1);
            String str = format.format(date);
            return str.substring(0, 1).toUpperCase() + str.substring(1);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
