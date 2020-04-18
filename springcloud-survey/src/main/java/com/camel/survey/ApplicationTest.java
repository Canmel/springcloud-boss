package com.camel.survey;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ApplicationTest {
    public static void main(String[] args) throws ParseException {
        Calendar calendar = new GregorianCalendar(2019, 11, 0);
        System.out.println(calendar.getActualMaximum(Calendar.DATE));
    }
}
