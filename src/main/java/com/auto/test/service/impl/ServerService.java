package com.auto.test.service.impl;

import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.auto.test.bean.SimpleJsonResult;
import com.auto.test.constant.Const;
import com.auto.test.exception.BusinessException;
import com.auto.test.service.IServerService;

@Service("serverService")
public class ServerService implements IServerService {
	private static Logger logger = LoggerFactory.getLogger(ServerService.class);

	@Override
	public SimpleJsonResult start(String server){
		try {
			exeShell(server, Const.SHELL_STARTUP);
			return new SimpleJsonResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new SimpleJsonResult(false, e.getMessage());
		}
	}
	
	@Override
	public SimpleJsonResult stop(String server){
		try {
			exeShell(server, Const.SHELL_SHUTDOWN);
			return new SimpleJsonResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new SimpleJsonResult(false, e.getMessage());
		}
	}
	
	@Override
	public SimpleJsonResult kill(String server) {
		try {
			exeShell(server, Const.SHELL_KILL);
			return new SimpleJsonResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new SimpleJsonResult(false, e.getMessage());
		}
	}
	
	private synchronized void exeShell(String server, String shell) throws IOException, InterruptedException{
		String [] command = null;
		if(System.getProperty("os.name").toLowerCase().contains("win")){
			String filePath = String.format(Const.PATH_TOMCAT_BIN, server) + shell + ".bat";
			if(isExists(filePath)){
				command = new String[]{"cmd", "/c", "start", "/b", filePath};
			}else{
				logger.error("文件不存在[" + filePath + "]");
				throw new BusinessException("文件不存在[" + Const.IP_CURRENT + "][" + filePath + "]");
			}
		}else{
			String filePath = String.format(Const.PATH_TOMCAT_BIN, server) + shell + ".sh";
			if(isExists(filePath)){
				command = new String[]{"/bin/sh", "-c", filePath};
			}else{
				logger.error("文件不存在[" + filePath + "]");
				throw new BusinessException("文件不存在[" + Const.IP_CURRENT + "][" + filePath + "]");
			}
		}
		Process process = null;
    	try {
    		process = Runtime.getRuntime().exec(command);
    		process.waitFor();
		} finally {
			if(process != null){
				process.destroy();
			}
		}
	}
	
	private boolean isExists(String filePath){
		File file = new File(filePath);
		if(file.exists() && file.isFile()){
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unused")
	private void sleep(int s){
		try {
			Thread.sleep(1000 * s);
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

}
