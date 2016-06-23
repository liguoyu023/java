package liguoyu.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 * 日期处理工具
 */
public class DateTimeUtil {

	public static String nowDate() {
		GregorianCalendar calenda = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(calenda.getTime());
	}

	/**
	 * 得到当前日期时间
	 */
	public static String now() {
		GregorianCalendar calenda = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(calenda.getTime());
	}
	
	/**
	 * 处理日期格式
	 */
	public static String timeFormatUtil(Date time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		return sdf.format(time);
	}
	
	/**
	 * 处理日期格式
	 */
	public static String timeFormatYmd(Date time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(time);
	}
	
	/**
	 *  
	 *  处理日期格式 
	 *  2016.4.26
	 */
	public static String timeFormatHms(Date time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(time);
	}

	/**
	 * 得到当前日期，默认格式2014-07-11
	 * 
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static String currentDate(String format) {
		GregorianCalendar calenda = new GregorianCalendar();
		SimpleDateFormat sdf;
		if (null == format || "".equals(format)) {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		} else {
			sdf = new SimpleDateFormat(format);
		}
		return sdf.format(calenda.getTime());
	}

	/**
	 * 得到day后的日期
	 */
	public static Date addDay(Date inDate, int day) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(inDate);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}

	/**
	 * 得到day后的日期
	 */
	public static Date addDay(Date inDate, int day,int second) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(inDate);
		calendar.add(Calendar.DATE, day);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND,second);
		return calendar.getTime();
	}

	/**
	 * 得到day后的日期
	 */
	public static String addDay(String strDate, String pattern, int day) {
		return date2Str(addDay(str2Date(strDate, pattern), day), pattern);
	}

	/**
	 * 得到day后的日期
	 */
	public static String addDay(Date date, String pattern, int day) {
		return date2Str(addDay(date, day), pattern);
	}

	/**
	 * 将日期转换为字符串
	 */
	public static String date2Str(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 字符串转化为日期
	 */
	public static Date str2Date(String dateStr, String pattern) {
		try {
			DateFormat parser = new SimpleDateFormat(pattern);
			return parser.parse(dateStr);
		} catch (ParseException ex) {
			return null;
		}
	}

	/**
	 * 得到今天是星期几
	 */
	public static int getWeek() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 得到当前月
	 * 
	 * @return
	 */
	public static String getMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1);
	}

	/**
	 * 判断是否是润年
	 */
	public static boolean isLeapYear(int year) {
		if ((year % 4) == 0 && ((year % 100) != 0 || (year % 400) == 0)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断当前时间是否在时间date2之前 时间格式 2005-4-21 16:16:34
	 */
	public static boolean isDateBefore(String date2) {
		try {
			Date date1 = new Date();
			DateFormat df = DateFormat.getDateTimeInstance();
			return date1.before(df.parse(date2));
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 判断时间date1是否在时间date2之前 时间格式 2005-4-21 16:16:34
	 */
	public static boolean isDateBefore(String date1, String date2) {
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			return df.parse(date1).before(df.parse(date2));
		} catch (ParseException e) {
			return false;
		}
	}

	
	
	/**
	 *  2016.5.23
	 */
	public static boolean isDateBefore(Date time) {
		try {
			Date curDate = new Date();
			return curDate.before(time);
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 *  2016.5.23
	 */
	public static boolean isDateAfter(Date time) {
		try {
			Date curDate = new Date();
			return curDate.after(time);
		} catch (Exception e) {
			return false;
		}
	}
	
	
	/**
	 * 将一个长整数转化为日前 比如1169713094344l转成日期2007-01-25 16:18:14
	 */
	public static String getDate(long d, String pattern) {
		Date c = new Date(d);
		return date2Str(c, pattern);
	}

	/**
	 * 计算两个日期相差的天数，如果date2 > date1 返回正数，否则返回负数
	 */
	public static long dayDiff(Date date1, Date date2) {
		return (date2.getTime() - date1.getTime()) / 86400000;
	}
	
	/**
	 */
	public static Date theEndOfNextYear() {
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.YEAR, 2);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DATE, 1);  
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		return calendar.getTime();
	}
	
	/**
	 * 根据生日获取星座
	 */
	public static String getAstro(String birth) {
		int month = Integer.parseInt(birth.substring(birth.indexOf("-") + 1, birth.lastIndexOf("-")));
		int day = Integer.parseInt(birth.substring(birth.lastIndexOf("-") + 1));
		String s = "魔羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯";
		int[] arr = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };
		int start = month * 2 - (day < arr[month - 1] ? 2 : 0);
		return s.substring(start, start + 2) + "座";
	}

	/**
	 * 判断日期是否有效,包括闰年的情况
	 */
	public static boolean isDate(String date) {
		StringBuffer reg = new StringBuffer("^((\\d{2}(([02468][048])|([13579][26]))-?((((0?");
		reg.append("[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))");
		reg.append("|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|");
		reg.append("(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12");
		reg.append("35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))");
		reg.append("-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
		reg.append("-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[");
		reg.append("1-9])|(1[0-9])|(2[0-8]))))))");
		Pattern p = Pattern.compile(reg.toString());
		return p.matcher(date).matches();
	}

	public static void main(String[] args) {
		// System.out.println(getDate(1398410445569783l,"yyyy-MM-dd HH:mm:ss"));
//		System.out.println(date2Str(str2Date("20120701", "yyyyMMdd"), "ddMMyyyy"));
		
		System.err.println(theEndOfNextYear());
	}
}