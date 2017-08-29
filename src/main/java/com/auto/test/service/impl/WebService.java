package com.auto.test.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.auto.test.bean.SimpleJsonResult;
import com.auto.test.constant.Const;
import com.auto.test.service.IWebService;
import com.auto.test.util.FileUtil;
import com.auto.test.util.IoUtil;
import com.auto.test.util.SvnUtil;

@Service("webService")
public class WebService implements IWebService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(WebService.class);

	@Override
	public SimpleJsonResult run(String url, String project) {
		FileUtil fu = new FileUtil();
		String path = Const.WEB_HOME + project + "/";
		String maxName = fu.maxFileNameByDir(path);
		String newName = fu.newMaxFileName(maxName);
		String newPath = path + newName;
		fu.createDir(newPath);
		new SvnUtil(Const.SVN_LKCZ_PUBLISH + url.replace("*", "/")).downloadSVNDir(newPath);
		new IoUtil().replaceFile(newPath + "/index.html", maxName, newName);
		return null;
	}

}
