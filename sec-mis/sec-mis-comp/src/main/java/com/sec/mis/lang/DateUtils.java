package com.sec.mis.lang;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

public class DateUtils extends org.apache.commons.lang.time.DateUtils {
	protected static Logger logger = Logger.getLogger(DateUtils.class.getName());

	/** 节假日列表 */
	public static List<Calendar> holidayList = new ArrayList<Calendar>();

	public static final String DAY_FORMAT = "yyyy-MM-dd";

	public static final String formatDate(Date date) {
		return formatDate(date, DAY_FORMAT);
	}

	/**
	 * 默认日期格式yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static final Date parseDate(String date) {
		return parseDate(date, DAY_FORMAT);
	}

	public static final Date parseDate(String date, String pattern) {
		try {
			return org.apache.commons.lang.time.DateUtils.parseDate(date, new String[] { pattern });
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static final Date parseDate(String date, String[] patterns) {
		try {
			return org.apache.commons.lang.time.DateUtils.parseDate(date, patterns);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static final String formatDate(Date date, String format) {

		SimpleDateFormat fmt = new SimpleDateFormat(DAY_FORMAT);

		if (format == null) {
			return fmt.format(date);

		} else {
			fmt = new SimpleDateFormat(format);
		}

		return fmt.format(date);
	}

	/**
	 * 日期格式化得到Date
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static final Date formatDateForDate(Date date, String format) {

		SimpleDateFormat fmt = new SimpleDateFormat(DAY_FORMAT);

		if (format == null) {
			return parseDate(fmt.format(date), DAY_FORMAT);

		} else {
			fmt = new SimpleDateFormat(format);
		}
		return parseDate(fmt.format(date), format);
	}

	public static final String formatSystime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	public static Date addDay(Date date, int day) {

		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		cal.add(Calendar.DAY_OF_MONTH, day);

		return cal.getTime();

	}

	public static Date addMonth(Date date, int month) {

		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		cal.add(Calendar.MONTH, month);

		return cal.getTime();

	}

	public static Date Long2Date(Long t) {

		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(t);

		return ca.getTime();

	}

	/**
	 * 日期格式化
	 * 
	 * @param c
	 * @param pattern
	 * @return
	 */
	public static String format(Calendar c, String pattern) {
		Calendar calendar = null;
		if (c != null) {
			calendar = c;
		} else {
			calendar = Calendar.getInstance();
		}
		if (pattern == null || pattern.equals("")) {
			pattern = "yyyy年MM月dd日 HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		return sdf.format(calendar.getTime());
	}

	/**
	 * 获取两个时间差
	 * 
	 * @deprecated
	 * @param beginDate
	 * @param endDate
	 * @return 相差天数
	 */
	public static Long getDatesDistanceForDay(Date beginDate, Date endDate) {
		beginDate = DateUtils.formatDateForDate(beginDate, DateUtils.DAY_FORMAT);
		endDate = DateUtils.formatDateForDate(endDate, DateUtils.DAY_FORMAT);
		return (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
	}
	
	/**
	 * 获取当天指定日期的时间
	 * 
	 * @return
	 */
	public static Date getFormatHourDate(Date date, int hour, int minite) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minite);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();

	}
	/**
	 * 获取从今天开始到下几个月的下一天
	 * 
	 * @param monthSum
	 *            取下几个月
	 * @return
	 */
	public static String getNextMonthNextDay(int monthSum) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date curDate = new Date();
		calendar.setTime(curDate);
		// 取得现在时间
		// System.out.println(sdf.format(curDate));
		// 取得上一个时间
		calendar.set(Calendar.MONDAY, calendar.get(Calendar.MONDAY) + monthSum);
		// 取得下一个月的下一天
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.get(Calendar.DAY_OF_MONTH) + 1);
		// System.out.println(sdf.format(calendar.getTime()));
		return sdf.format(calendar.getTime());
	}

	/**
	 * 获取某天的下个月的下一天
	 * 
	 * @param monthSum
	 * @return
	 */
	public static String getDayNextMonthNextDay(String date, int monthSum) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			// Date curDate = new Date();
			calendar.setTime(sdf.parse(date));
			// 取得现在时间
			// System.out.println(sdf.format(curDate));
			// 取得上一个时间
			calendar.set(Calendar.MONDAY, calendar.get(Calendar.MONDAY)
					+ monthSum);
			// 取得下一个月的下一天
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.get(Calendar.DAY_OF_MONTH) + 1);
			// System.out.println(sdf.format(calendar.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdf.format(calendar.getTime());
	}

	/**
	 * 获取某天的下几个月的某天
	 * 
	 * @param date
	 * @param monthSum
	 * @return
	 */
	public static String getNextMonthCurrentDay(String date, int monthSum) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			// Date curDate = new Date();
			calendar.setTime(sdf.parse(date));
			// 取得现在时间
			// System.out.println(sdf.format(curDate));
			// 取得上一个时间
			// calendar.set(Calendar.MONDAY, calendar.get(Calendar.MONDAY) +
			// monthSum);
			// 取得下一个月的当天
			// calendar.set(Calendar.DAY_OF_MONTH,
			// calendar.get(Calendar.DAY_OF_MONTH));
			// System.out.println(sdf.format(calendar.getTime()));

			calendar.add(Calendar.MONTH, monthSum);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdf.format(calendar.getTime());
	}
	/**
	 * 获取某日期的最后一天日期
	 * 
	 * @param invoiceMonth
	 *            格式：201108
	 * @return
	 */
	public static String getInvoiceMonth(String invoiceMonth) {// invoiceMonth
																// 的格式为 201108
		String str = "";
		try {
			String year = invoiceMonth.substring(0, 4);
			String month = invoiceMonth.substring(4, invoiceMonth.length());
			Calendar date = Calendar.getInstance();
			int yeari = Integer.parseInt(year);
			int monthi = Integer.parseInt(month);
			// if(monthi==1){
			// monthi=12;
			// }
			date.set(yeari, monthi - 1, 1);
			int maxDayOfMonth = date.getActualMaximum(Calendar.DAY_OF_MONTH);
			str = year + "-" + monthi + "-" + maxDayOfMonth;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 当月第一天
	 * 
	 * @return
	 */
	public static String getFirstDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date theDate = calendar.getTime();
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(gcLast.getTime());
		// StringBuffer str = new
		// StringBuffer().append(day_first).append(" 00:00:00");
		System.out.println("当月第一天：" + day_first);
		return day_first;
	}

	/**
	 * 当月最后一天
	 * 
	 * @return
	 */
	public static String lastDayOfMonth(String da) {
		String d = null;
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(da);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.roll(Calendar.DAY_OF_MONTH, -1);
			// d = cal.getTime();
			d = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * 两日期时间相差的秒数
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static Long getDateBetweenSecond(String start, String end) {
		long betweenDate = 0;
		try {
			java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			java.util.Date startDate = df.parse(start);
			java.util.Date endDate = df.parse(end);
			betweenDate = (endDate.getTime() - startDate.getTime()) / 1000;
		} catch (Exception e) {
			logger.info("**** ERROR 两日期时间相差的秒数 Utils.getDateBetweenSecond "
					+ e.getMessage());
			e.printStackTrace();
		}
		return betweenDate;
	}
	
	/**
	 * 获取当前日期字符串2
	 * 
	 * @return
	 */
	public static String getNowDateStr2() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//
		return df.format(new Date());
	}

	/**
	 * 获取当前时间字符串
	 * 
	 * @return
	 */
	public static String getNowTimeStr() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//
		return df.format(new Date());
	}

	/**
	 * 获取当前时间，精确到微秒
	 * 
	 * @return
	 */
	public static String getNowTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");//
		return df.format(new Date());
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @deprecated 不再使用
	 * @see #parseDate(String)
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str, String dateFormate) {
		SimpleDateFormat format;
		if (dateFormate == null || "".equals(dateFormate)) {
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else {
			format = new SimpleDateFormat(dateFormate);
		}
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取两个时间相差的月份和天数：小于一个月返回0月
	 * 
	 * @deprecated
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public static DateDistanceVo getDatesDistance(Date beginDate, Date endDate) {
		DateDistanceVo res = new DateDistanceVo();
		beginDate = DateUtils.formatDateForDate(beginDate, DateUtils.DAY_FORMAT);
		endDate = DateUtils.formatDateForDate(endDate, DateUtils.DAY_FORMAT);
		beginDate = addMonth(beginDate, 1);
		int month = 0;
		while (beginDate.compareTo(endDate) == -1 || beginDate.compareTo(endDate) == 0) {// 开始日期小于或者结束日期
			month++;
			beginDate = addMonth(beginDate, 1);
		}
		beginDate = addMonth(beginDate, -1);
		Long day = getDatesDistanceForDay(beginDate, endDate);
		res.setDayDistance(day.intValue());
		res.setMonthDistance(month);
		return res;
	}

	/**
	 * 根据开始和结束时间，判断当前时间是在整月数据中还是在剩余天数中：如2015.1.1到2015.5.20 有4个整月数，19天于天数，假如今天是2015.3.4就是在整月数中
	 * 
	 * @deprecated
	 * @param beginDate
	 * @param endDate
	 * @return 在正月数中返回0，在剩余天数中返回1
	 */
	public static int getWhereExits(Date beginDate, Date endDate) {
		beginDate = DateUtils.formatDateForDate(beginDate, DateUtils.DAY_FORMAT);
		endDate = DateUtils.formatDateForDate(endDate, DateUtils.DAY_FORMAT);
		DateDistanceVo dateDistance = getDatesDistance(beginDate, endDate);
		if (dateDistance.getMonthDistance() == 0) return 1;// 处于剩余天数中
		int month = dateDistance.getMonthDistance();
		Date maxMonthDate = addMonth(beginDate, month);// 开始日期加上正月数
		// 判断开始日期加上正月数和当前时间
		int compare = maxMonthDate.compareTo(DateUtils.formatDateForDate(new Date(), DateUtils.DAY_FORMAT));
		if (compare == 1 || compare == 0) {// 开始日期加上正月数大于等于当前时间，处于整月数中
			return 0;
		} else {
			return 1;
		}

	}

	public static int diffDate(Date d1, Date d2) {
		if ((d1 == null) || (d2 == null)) return 0;

		Calendar cal = Calendar.getInstance();

		int zoneoffset = cal.get(Calendar.ZONE_OFFSET);
		int dstoffset = cal.get(Calendar.DST_OFFSET);

		long dl1 = d1.getTime() + zoneoffset + dstoffset;
		long dl2 = d2.getTime() + zoneoffset + dstoffset;

		int intDaysFirst = (int) (dl1 / (60 * 60 * 1000 * 24)); // 60*60*1000
		int intDaysSecond = (int) (dl2 / (60 * 60 * 1000 * 24));

		return intDaysFirst > intDaysSecond ? intDaysFirst - intDaysSecond : intDaysSecond - intDaysFirst;
	}

	/**
	 * 截断小时分钟秒，精确到天
	 * 
	 * @param date
	 * @return
	 */
	public static Date truncateToDay(Date date) {
		return DateUtils.truncate(date, Calendar.DATE);
	}

	public static Date transMillis2Date(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	public static Double daysBetween(Date start, Date end) {
		BigDecimal num1 = BigDecimal.valueOf(end.getTime());
		BigDecimal num2 = BigDecimal.valueOf(start.getTime());
		return num1.subtract(num2).divide(BigDecimal.valueOf(24 * 60 * 60 * 1000), 2, RoundingMode.HALF_UP).doubleValue();
	}
	
	public static int daysBetween2(Date start, Date end) {
		BigDecimal num1 = BigDecimal.valueOf(end.getTime());
		BigDecimal num2 = BigDecimal.valueOf(start.getTime());
		return num1.subtract(num2).divide(BigDecimal.valueOf(24 * 60 * 60 * 1000), 0, RoundingMode.HALF_UP).intValue();
	}

	public static void main(String[] args) {
		DateDistanceVo vo = getDatesDistance(parseDate("2015-01-28", DateUtils.DAY_FORMAT), parseDate("2015-05-27", DateUtils.DAY_FORMAT));
		System.out.println(vo.getDayDistance());

		System.out.println(DateUtils.getSunday(new Date()));
		System.out.println(DateUtils.getSaturday(new Date()));
	}

	// 获取当前的结束时间,精确到秒
	public static Date setEndOfDay(Date time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 计算前n个工作日或者后n个工作日
	 * 
	 * @param src日期
	 *            (源)
	 * @param addOrMinu
	 *            向前传-1，向后传1
	 * @param adddays
	 *            要加的天数
	 */
	public static Date addDateByWorkDay(Date date, int days, int addOrMinu) {

		date = formatDateForDate(date, null);
		if (addOrMinu == 1 || addOrMinu == -1) {

		} else {
			return null;
		}
		boolean holidayFlag = false;
		Calendar src = Calendar.getInstance();
		src.setTime(date);
		for (int i = 0; i < days; i++) {
			// 把源日期加一天
			src.add(Calendar.DAY_OF_MONTH, addOrMinu);
			holidayFlag = checkHoliday(src);
			if (holidayFlag) {
				i--;
			}
		}
		return src.getTime();
	}

	/**
	 * 校验指定的日期是否在节日列表中 具体节日包含哪些,可以在HolidayMap中修改
	 * 
	 * @param src
	 *            要校验的日期(源)
	 */
	public static boolean checkHoliday(Calendar src) {
		boolean result = false;
		// 先检查是否是周六周日
		if (src.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || src.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return true;
		}

		for (Calendar c : holidayList) {
			if (src.get(Calendar.MONTH) == c.get(Calendar.MONTH) && src.get(Calendar.DAY_OF_MONTH) == c.get(Calendar.DAY_OF_MONTH)) {
				result = true;
			}
		}

		return result;
	}

	/**
	 * Date 转化Calendar
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar calendar2Date(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 判断指定时间是否在某时间范围内
	 * 
	 * @param date
	 *            指定时间
	 * @param begin
	 *            开始时间
	 * @param includeBegin
	 *            是否包括开始时间
	 * @param end
	 *            结束时间
	 * @param includeEnd
	 *            是否包括结束时间
	 * @param field
	 *            精确度，精确到日，小时，分钟，秒
	 * @return
	 */
	public static boolean between(Date date, Date begin, boolean includeBegin, Date end, boolean includeEnd, int field) {
		Date $date = DateUtils.truncate(date, field);
		Date $begin = DateUtils.truncate(begin, field);
		Date $end = DateUtils.truncate(end, field);
		boolean bool = $begin.before($date) || (includeBegin && !$begin.after($date));
		bool = bool && ($end.after($date) || (includeEnd && !$end.before($date)));
		return bool;
	}

	/**
	 * 判断指定日期是否在某日期范围内
	 * 
	 * @param date
	 *            指定日期
	 * @param begin
	 *            开始日期，包括开始日期
	 * @param end
	 *            结束日期，包括结束日期
	 * @return begin &lt;= date &lt;= end 返回true
	 */
	public static boolean between(Date date, Date begin, Date end) {
		return between(date, begin, true, end, true, Calendar.DATE);
	}

	/**
	 * 判断指定日期是否在某日期范围内
	 * 
	 * @param date
	 *            指定日期
	 * @param begin
	 *            开始日期，包括开始日期
	 * @param end
	 *            结束日期，不包括结束日期
	 * @param includeEnd
	 *            是否包括结束日期
	 * @return
	 */
	public static boolean between(Date date, Date begin, Date end, boolean includeEnd) {
		return between(date, begin, true, end, includeEnd, Calendar.DATE);
	}

	public static boolean before(Date earlier, Date later, boolean equals, int field) {
		Date $earlier = DateUtils.truncate(earlier, field);
		Date $later = DateUtils.truncate(later, field);
		return $earlier.before($later) || (equals && !$earlier.after($later));
	}

	public static int[] paseDate(Date date) {

		String[] s = formatDate(date, "HH:mm:ss").split(":");
		int[] a = new int[3];
		a[0] = Integer.parseInt(s[0]);
		a[1] = Integer.parseInt(s[1]);
		a[2] = Integer.parseInt(s[2]);

		return a;
	}

	/**
	 * 得到上月时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastMonthDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 得到指定日期月初日期
	 * 
	 * @return
	 */
	public static Date getMonthStart(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 得到指定日期月末日期
	 * 
	 * @return
	 */
	public static Date getMonthEnd(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	/**
	 * 得到指定日期周日时间(周第一天)
	 * 
	 * @param date
	 * @return
	 */
	public static Date getSunday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return cal.getTime();
	}

	/**
	 * 得到指定日期周六时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getSaturday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		return cal.getTime();
	}
	/**
	 * 获取当前时间的时分秒
	 * @return
	 */
	public static int[] getIntTime() {
		
		int [] ret=new int[3];
		Calendar cal = Calendar.getInstance();
		ret[0]=cal.get(Calendar.HOUR_OF_DAY);
		ret[1]=cal.get(Calendar.MINUTE);
		ret[2]=cal.get(Calendar.SECOND);
		return ret;
	}
	

}