package com.auto.test.service.impl;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.auto.test.bean.SimpleJsonResult;
import com.auto.test.service.ITimeService;
import com.auto.test.util.DateUtil;

@Service("timeService")
public class TimeService implements ITimeService {
	private static Logger logger = LoggerFactory.getLogger(TimeService.class);

	@Override
	public SimpleJsonResult getDateTime() {
		try {
			return new SimpleJsonResult(true, String.valueOf(DateUtil.getLongSystemTime()));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new SimpleJsonResult(false, e.getMessage());
		}
	}

	@Override
	public SimpleJsonResult setDateTime(String time) {
		if(time == null || !time.isEmpty()){
			return new SimpleJsonResult(false, "设置时间不能为空！");
		}
		try {
			DateUtil.setSystemDateTime(new Date(Long.parseLong(time)));
			return new SimpleJsonResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new SimpleJsonResult(false, e.getMessage());
		}
	}

}
