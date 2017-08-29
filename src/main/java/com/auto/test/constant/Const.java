package com.auto.test.constant;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Const {
	
	public static final String PATH_WEB				= "/usr/local/nginx/html/";
	public static final String PATH_TOMCAT			= "/data/tomcat";
	public static final String PATH_TOMCAT_PROJECT	= PATH_TOMCAT + File.separator + "%s";
	public static final String PATH_TOMCAT_BIN		= Const.PATH_TOMCAT_PROJECT + File.separator + "bin" + File.separator;
	public static final String PATH_TOMCAT_WEBAPPS	= Const.PATH_TOMCAT_PROJECT + File.separator + "webapps" + File.separator;

	public static final String SHELL_KILL			= "kill";
	public static final String SHELL_SHUTDOWN		= "shutdown";
	public static final String SHELL_STARTUP		= "startup";
	
	public static final String SVN_USERNAME 		= "zhouzhou";
	public static final String SVN_PASSWORD 		= "Jih3wroK1d19yerM";
	public static final String SVN_LKCZ_PUBLISH		= "https://61.155.136.217:8443/svn/LKCZ/publish/";
	public static final String WEB_HOME				= "/usr/local/nginx/html/";
	
	public static final String IP_CURRENT			= getCurrentIP();
	public static String getCurrentIP(){
		try {
			return InetAddress.getLocalHost().getHostAddress().trim();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
