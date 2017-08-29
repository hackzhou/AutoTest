package com.auto.test.service.impl;

import org.springframework.stereotype.Service;
import com.auto.test.bean.SimpleJsonResult;
import com.auto.test.context.SpringContext;
import com.auto.test.service.IMyTestService;

@Service("myService")
public class MyTestService implements IMyTestService {

	@Override
	public SimpleJsonResult my() {
		return ((FileService) SpringContext.getBean("fileService")).listProjects();
	}

}
