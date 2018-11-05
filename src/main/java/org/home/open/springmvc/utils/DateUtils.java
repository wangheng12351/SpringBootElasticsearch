package org.home.open.springmvc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		System.out.println(sdf.format(new Date()));
	}
	/**********
	 * 2018-07-26 17:52:58 转成017-12-21T03:16:55.000Z
	 * @param time
	 * @return
	 */
	public static String dateToUtc (String time) {
		String dateTime="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date curendDate = sdf1.parse(time);
			dateTime = sdf.format(curendDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateTime;
	}
	/*************
	 * 判断是否在时间范围内
	 * @param time 当前时间
	 * @param begintime 开始时间
	 * @param endtime 结束时间
	 * @return
	 */
	public static boolean comparedDataTime(int time,int begintime,int endtime) {
		boolean flag = false;
		if (time >= begintime && time <= endtime) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}
}
