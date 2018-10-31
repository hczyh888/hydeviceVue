package com.gz.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间处理类
 */
public class TimeUtil {
	
	
	/**
	 * 将Date类型的时间转换为 一般形式 字符串 yyyyMMddHHmmss
	 */
	public static String date2Str0(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
		return formater.format(date);
	}
	/**
	 * 将Date类型的时间转换为 一般形式 字符串 1985-12-05 12:00:00
	 */
	public static String date2Str1(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS", Locale.CHINA);
		return formater.format(date);
	}

	/**
	 * 将Date类型的时间转换为 一般形式 字符串 1985-12-05 12:00
	 */
	public static String date2Str2(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
		return formater.format(date);
	}

	/**
	 * 将Date类型的时间转换为 一般形式 字符串 1985-12-05
	 */
	public static String date2Str3(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		return formater.format(date);
	}

	/**
	 * 将Date类型的时间转换为 一般形式 字符串 1985/12/05
	 */
	public static String date2Str5(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
		return formater.format(date);
	}

	/**
	 * 将Date类型的时间转换为 一般形式 字符串 03-25 15:30
	 */
	public static String date2Str4(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
		return formater.format(date);
	}
	
	/**
	 * 将Date类型的时间转换为 一般形式 字符串 12:00
	 */
	public static String date2Str6(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("HH:mm", Locale.CHINA);
		return formater.format(date);
	}

	/**
	 * 将Date类型的时间转换为 一般形式 字符串 19851205
	 */
	public static String date2Str7(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
		return formater.format(date);
	}

	/**
	 * 将Date类型的时间转换为 一般形式 字符串 12-09
	 */
	public static String date2Str8(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("MM-dd", Locale.CHINA);
		return formater.format(date);
	}
	/**
	 * 将Date类型的时间转换为 一般形式 字符串 2017-01
	 */
	public static String date2Str9(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM", Locale.CHINA);
		return formater.format(date);
	}

	/**
	 * 将Date类型的时间转换为 一般形式 字符串 1985/12/05 12:00
	 */
	public static String date2Str10(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.CHINA);
		return formater.format(date);
	}
	public static String date2Year(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy", Locale.CHINA);
		return formater.format(date);
	}

	public static String date2Month(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("MM", Locale.CHINA);
		return formater.format(date);
	}

	public static String date2Day(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("dd", Locale.CHINA);
		return formater.format(date);
	}
	
	public static String date2Hour(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("HH", Locale.CHINA);
		return formater.format(date);
	}

	
	/**
	 * 将字符串的时间转换为Date类型
	 * 
	 * @param timeStr
	 *             yyyyMMddHHmmss
	 * @return 失败返回null
	 */
	public static Date str2Date0(String timeStr) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
		try {
			return formater.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将字符串的时间转换为Date类型
	 * 
	 * @param timeStr
	 *            1985-12-05 12:00:00
	 * @return 失败返回null
	 */
	public static Date str2Date1(String timeStr) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		try {
			return formater.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将字符串的时间转换为Date类型
	 * 
	 * @param timeStr
	 *            1985-12-05 12:00
	 * @return 失败返回null
	 */
	public static Date str2Date2(String timeStr) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
		try {
			return formater.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将字符串的时间转换为Date类型
	 * 
	 * @param timeStr
	 *            1985-12-05
	 * @return 失败返回null
	 */
	public static Date str2Date3(String timeStr) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		try {
			return formater.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将字符串的时间转换为Date类型
	 * 
	 * @param timeStr
	 *            1985/12/05
	 * @return 失败返回null
	 */
	public static Date str2Date5(String timeStr) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
		try {
			return formater.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将字符串的时间转换为Date类型
	 * 
	 * @param timeStr
	 *            03-25 15:30
	 * @return 失败返回null
	 */
	public static Date str2Date4(String timeStr) {
		SimpleDateFormat formater = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
		try {
			return formater.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 日期格式字符串转换成时间戳
	 * 
	 * @param date
	 *            字符串日期
	 * @param format
	 *            如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String date2TimeStamp(String date_str) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return String.valueOf(sdf.parse(date_str).getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 检查时间字符串是否符合正确的时间格式。eg: MM/dd/yyyy下：12/33/2014是错误的
	 * 
	 * @param dateDtring
	 *            eg:12/12/2014
	 * @param dateFormatPattern
	 *            eg:MM/dd/yyyy
	 * @return 是否正确
	 */
	public static boolean validDate(String dateDtring, String dateFormatPattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormatPattern);
		sdf.setLenient(false);
		try {
			sdf.parse(dateDtring);
		} catch (ParseException e) {
			// e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 距离现在的时间 毫秒
	 * 
	 * @param date
	 * @return
	 */
	public static long howFarInMillis(Date date) {
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		long nowMillis = now.getTime();
		long targetMillis = date.getTime();
		return Math.abs(nowMillis - targetMillis);
	}

	/**
	 * 距离现在的时间 秒
	 * 
	 * @param date
	 * @return
	 */
	public static long howFarInSecond(Date date) {
		return howFarInMillis(date) / 1000;
	}

	/**
	 * 距离现在的时间 分钟
	 * 
	 * @param date
	 * @return
	 */
	public static int howFarInMinute(Date date) {
		return (int) (howFarInSecond(date) / 60);
	}

	/**
	 * 距离现在的时间 小时
	 * 
	 * @param date
	 * @return
	 */
	public static int howFarInHour(Date date) {
		return (int) (howFarInMinute(date) / 60);
	}

	/**
	 * 距离现在的时间 天
	 * 
	 * @param date
	 * @return
	 */
	public static int howFarInDay(Date date) {
		return (int) (howFarInHour(date) / 24);
	}

	/**
	 * 根据出生日期计算年龄
	 * @param dateOfBirth
	 * @return
	 */
	public static int getAge(Date dateOfBirth) {
		int age = 0;
		Calendar born = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		if (dateOfBirth != null) {
			now.setTime(new Date());
			born.setTime(dateOfBirth);
			if (born.after(now)) {
				return 0;
			}
			age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
			if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
				age -= 1;
			}
		}
		return age;
	}
	
	/**
	 * 判断time时间是否在给定的时间区间
	 * @param timeRang 如00:00-24:00
	 * @param time 给定的时间
	 * @return
	 */
	public static boolean isInTime(String timeRang,long time){
		if(!timeRang.contains("-")){
			return false;
		}
		String[] times=timeRang.split("-");
		String startTime=times[0];
		String endTime=times[1];
		
		String dayStr=TimeUtil.date2Str3(new Date(System.currentTimeMillis()));
		startTime=dayStr+" "+startTime;
		endTime=dayStr+" "+endTime;
		long start=TimeUtil.str2Date2(startTime).getTime();
		long end=TimeUtil.str2Date2(endTime).getTime();
		if(time>=start&&time<=end){
			return true;
		}else{
			return false;
		}
	}
	
	/**
     * 获取当前日期是星期几 
     * 
     * @param dt
     * @return 当前日期是星期几,如1
     */
    public static int getIntWeekOfDate(Date dt) {
        int[] weekDays = {7, 1,2, 3, 4, 5, 6};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
	
	 /**
     * 获取当前日期是星期几 
     * 
     * @param dt
     * @return 当前日期是星期几,如星期一
     */
    public static String getStringWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    
    /**
     * @param num   负数 ，-3表示近三个月
     * @return date2Str5
     */
    public static String howFarMouth(int num){
    	Calendar c = Calendar.getInstance();
        c.setTime(new Date(System.currentTimeMillis()));
        c.add(Calendar.MONTH,num);
    	return date2Str5(c.getTime());
    }
    


    /**
     * 获取某月有多少天
     * @param month  月
     * @param year   年
     * @return
     */
	private static String getDays(String month,String year){
		String endDay = "30";
		if ("01".equals(month) || "03".equals(month) || "05".equals(month) || "07".equals(month) || "08".equals(month)
				|| "10".equals(month) || "12".equals(month)) {
			endDay = "31";
		} else if ("02".equals(month)) {
			if ("00".equals(year.substring(2, 4)) && Integer.parseInt(year) % 400 == 0) {
				endDay = "29";
			} else if (Integer.parseInt(year) % 4 == 0) {
				endDay = "29";
			} else {
				endDay = "28";
			}
		}
		return endDay;
	}

	/**
	 * 获取某天0点
	 * @param date
	 * @return
	 */
	public   static Date getDayStart(Date date){
		Calendar calendar = Calendar.getInstance();
		if(date!=null) {
			calendar.setTime(date);
		}else{
			calendar.setTime(new Date());
		}
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date zero = calendar.getTime();
		return zero;
	}
	/**
	 * 获取某天59:59
	 * @param date
	 * @return
	 */
	public  static Date getDayEnd(Date date){
		Calendar calendar = Calendar.getInstance();
		if(date!=null) {
			calendar.setTime(date);
		}else{
			calendar.setTime(new Date());
		}
		calendar.set(Calendar.HOUR_OF_DAY, 13);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		Date end = calendar.getTime();
		return end;
	}
	//需要注意的是：月份是从0开始的，比如说如果输入5的话，实际上显示的是4月份的最后一天，千万不要搞错了哦
	public static Date getLastDayOfMonth(Date date) {
		Calendar temp = Calendar.getInstance();
		temp.setTime(date);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,temp.get(Calendar.YEAR));
		cal.set(Calendar.MONTH, temp.get(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
		return   cal.getTime();
	}
	public static Date getFirstDayOfMonth(Date date) {
		Calendar temp = Calendar.getInstance();
		temp.setTime(date);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,temp.get(Calendar.YEAR));
		cal.set(Calendar.MONTH, temp.get(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));
		return   cal.getTime();
	}

	/**
	 * 判断两个时间在统一小时内
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean checkInOnehour(Date date1,Date date2) {
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime(date1);
		calendar2.setTime(date2);
		if(calendar1.get(Calendar.YEAR)==calendar2.get(Calendar.YEAR)&&
				calendar1.get(Calendar.MONTH)==calendar2.get(Calendar.MONTH)&&
				calendar1.get(Calendar.DAY_OF_MONTH)==calendar2.get(Calendar.DAY_OF_MONTH)&&
				calendar1.get(Calendar.HOUR_OF_DAY)==calendar2.get(Calendar.HOUR_OF_DAY)){
			return true;
		}
		return false;
	}

}
