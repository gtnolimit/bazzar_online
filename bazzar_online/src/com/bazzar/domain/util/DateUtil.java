package com.bazzar.domain.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

	public static Date setDate(String date) {
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			return new Date();
		}

	}
	public static String dateToString (Date date, String format){
        DateFormat formatter ;     
        formatter = new SimpleDateFormat(format);
	    return formatter.format(date);
	}
	
	public static Date getFormatedDate(String date, String format) {
		try {
			DateFormat formatDate = new SimpleDateFormat(format);
			return formatDate.parse(date);
		} catch (Exception e) {
			 e.printStackTrace();
			 return null;
		}
	}

	public static String getFormatedDate(Date date, String format) {
		try {

			DateFormat formatDate = new SimpleDateFormat(format, new Locale("cs", "CS"));
		    
			return formatDate.format(date);
		} catch (Exception e) {
			 e.printStackTrace();
			 return null;
		}
	}
	
	public static Date setDate(Date unformatedDate) {
		try {
			return dateFormat.parse(unformatedDate.toString());
		} catch (ParseException e) {
			return new Date();
		}
	}

	public static boolean compare(Date d1, Date d2) {
		return d1.equals(d2);
	}

	public static Date getDate() {
		return new Date();
	}

	public static long yearsBetween(Date start, Date end) {
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(start);
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(end);
		Calendar date = (Calendar) startDate.clone();
		long daysBetween = 0;
		while (date.get(Calendar.YEAR)<endDate.get(Calendar.YEAR)) {
			date.add(Calendar.YEAR, 1);
			daysBetween++;
		}
		return daysBetween;
	}

	public static long daysBetween(Date start, Date end) {
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(start);
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(end);
		Calendar date = (Calendar) startDate.clone();
		long daysBetween = 0;
		while (date.before(endDate)) {
			date.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}
		return daysBetween;
	}

	public static Date decrementYears(Date deadLine, int years){
		Calendar newDate = Calendar.getInstance();
		newDate.setTime(deadLine);
		int newYear = newDate.get(Calendar.YEAR) - years;
		newDate.set(Calendar.YEAR, newYear);
		return newDate.getTime();
	}
	
}

