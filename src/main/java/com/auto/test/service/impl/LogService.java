package com.auto.test.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.auto.test.bean.SimpleJsonResult;
import com.auto.test.context.LogContext;
import com.auto.test.context.SpringContext;
import com.auto.test.service.ILogService;

@Service("logService")
public class LogService implements ILogService {
	private static Logger logger = LoggerFactory.getLogger(LogService.class);
	
	@Override
	public SimpleJsonResult isRun() {
		try {
			LogContext logContext = (LogContext) SpringContext.getBean("logContext");
			if(logContext.isRun()){
				return new SimpleJsonResult(true, "[查看日志]正在运行", logContext.getName());
			}
			return new SimpleJsonResult(false, "[查看日志]停止运行", logContext.getName());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new SimpleJsonResult(false, e.getMessage());
		}
	}

	@Override
	public SimpleJsonResult start(String server) {
		try {
			LogContext logContext = (LogContext) SpringContext.getBean("logContext");
			if(logContext.isRun()){
				return new SimpleJsonResult(false, "[查看日志]启动失败，正在运行", logContext.getName());
			}
			logContext.exe(server);
			return new SimpleJsonResult(true, "[查看日志]启动成功", logContext.getName());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new SimpleJsonResult(false, e.getMessage());
		}
	}

	@Override
	public SimpleJsonResult read() {
		try {
			LogContext logContext = (LogContext) SpringContext.getBean("logContext");
			if(!logContext.isRun()){
				return new SimpleJsonResult(false, "[查看日志]读取失败，停止运行", logContext.getName());
			}
			return new SimpleJsonResult(true, logContext.read(), logContext.getName());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new SimpleJsonResult(false, e.getMessage());
		}
	}

	@Override
	public SimpleJsonResult stop() {
		try {
			LogContext logContext = (LogContext) SpringContext.getBean("logContext");
			logContext.stop();
			return new SimpleJsonResult(true, "[查看日志]停止成功", logContext.getName());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new SimpleJsonResult(false, e.getMessage());
		}
	}

}
