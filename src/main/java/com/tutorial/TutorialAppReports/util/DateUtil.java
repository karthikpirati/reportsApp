package com.tutorial.TutorialAppReports.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static final String DDMMYYY="dd-MM-YYYY";
	
	public static String dateTostring(Date date,String format) {
		SimpleDateFormat sd=new SimpleDateFormat(format);
		return sd.format(date);
	}

}
