package com.auto.test.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static void main(String[] args) {
		System.out.println(getFormatDateTime(getNetworkTime()));	//网络时间
		System.out.println(getFormatDateTime(getSystemTime()));		//本地时间
		setSystemDateTime(null);
	}
	
	/**
	 * 设置本地时间(date为空时为网络标准时间)
	 * @param date
	 */
	public static void setSystemDateTime(Date date){
		date = (date == null) ? getNetworkTime() : date;
		String os = System.getProperty("os.name");
		if(os.toLowerCase().contains("windows")){
			String dateCom[] = {"cmd", "/c", "date", getFormatDate(date)};
			exeCommand(dateCom);
			String timeCom[] = {"cmd", "/c", "time", getFormatTime(date)};
			exeCommand(timeCom);
		}else if(os.toLowerCase().contains("linux")){
			String dateCom[] = {"date", "-s", getFormatDateTime(date)};
			exeCommand(dateCom);
			String cmosCom[] = {"hwclock", "–w"};
			exeCommand(cmosCom);
		}
	}
	
	/**
	 * 获取本地系统时间
	 * @return
	 */
	public static Long getLongSystemTime(){
        return System.currentTimeMillis();
    }
	
	/**
	 * 获取本地系统时间
	 * @return
	 */
	public static Date getSystemTime(){
        return new Date();
    }
	
	/**
	 * 获取网络标准时间
	 * @return
	 */
	public static Date getNetworkTime(){
        try{
            URL url = new URL("http://www.baidu.com");
            URLConnection urlConn = url.openConnection();
            urlConn.connect();
            long time = urlConn.getDate();
            return new Date(time);
        }catch(MalformedURLException e){
        	e.printStackTrace();
        }catch(IOException e){
        	e.printStackTrace();
        }
        return null;
    }
	
	private static void exeCommand(String[] command){
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(command);
			process.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(process != null){
				process.destroy();
			}
		}
	}
	
	public static String getFormatDateTime(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	public static String getFormatDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
	public static String getFormatTime(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(date);
	}
	
	public static String date2String(Date date, String format){
		return new SimpleDateFormat(format).format(date);
	}
	
	public static Date string2Date(String text, String format){
		try {
			return new SimpleDateFormat(format).parse(text);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
