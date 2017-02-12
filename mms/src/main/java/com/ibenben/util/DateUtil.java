package com.ibenben.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	/**
	 * ************************************************************************************
	 * 获取当天的前一天
	 *************************************************************************************
	 */
	public static Date getLastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return date;
	}
	
	/**
	 * ************************************************************************************
	 * 获取当天的后一天
	 *************************************************************************************
	 */
	public static Date getNextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		date = calendar.getTime();
		return date;
	}
	
	/**
	 * ************************************************************************************
	 * 获取当天的起始时间
	 *input 2016-08-23 12:02:02  return 2016-08-23 00:00:00
	 *************************************************************************************
	 */
	public static Date getZeroDate(Date date) throws ParseException{
		if(date == null) {
			return null;	
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(sdf.format(date));
	}
	
	public static String format(Date date) throws ParseException{
		if(date == null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(date);
	}
	
}
