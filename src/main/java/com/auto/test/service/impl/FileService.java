package com.auto.test.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.activation.DataHandler;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.auto.test.bean.SimpleJsonResult;
import com.auto.test.constant.Const;
import com.auto.test.service.IFileService;

@Service("fileService")
public class FileService implements IFileService {
	private static Logger logger = LoggerFactory.getLogger(FileService.class);
	
	@Override
	public SimpleJsonResult listProjects() {
		return getFileDirNames(Const.PATH_TOMCAT);
	}
	
	@Override
	public SimpleJsonResult listWebProjects() {
		return getFileDirNames(Const.PATH_WEB);
	}
	
	private SimpleJsonResult getFileDirNames(String path){
		List<String> list = new ArrayList<String>();
		try {
			File file = new File(path);
			if(file.exists()){
				for (File f : file.listFiles()) {
					if(f.exists() && f.isDirectory()){
						list.add(f.getName());
					}
				}
			}
			Collections.sort(list, new Comparator<String>(){
	            public int compare(String o1, String o2) {
	                return o1.toString().compareTo(o2.toString());
	            }
	        });
			return new SimpleJsonResult(true, list, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new SimpleJsonResult(false, e.getMessage());
		}
	}

	@Override
	public SimpleJsonResult uploadFile(Attachment file, String server) {
		try {
			DataHandler dh = file.getDataHandler();
			InputStream is = dh.getInputStream();
			writeToFile(is, server, dh.getName());
			return new SimpleJsonResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new SimpleJsonResult(false, e.getMessage());
		}
	}
	
	private void writeToFile(InputStream is, String server, String name) throws IOException {
		OutputStream out = null;
        try {
            out = new FileOutputStream(new File(String.format(Const.PATH_TOMCAT_WEBAPPS, server) + name));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = is.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
        } finally {
        	try {
        		if(out != null){
        			out.close();
        		}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
    }

	@Override
	public SimpleJsonResult deleteFile(String server, String name) {
		try {
			String webAppsProject = String.format(Const.PATH_TOMCAT_WEBAPPS, server) + name;
			File file = new File(webAppsProject + ".war");
			if(file.exists() && file.isFile()){
				file.delete();
			}
			File dir = new File(webAppsProject);
			if(dir.exists() && dir.isDirectory()){
				deleteFileDir(dir);
			}
			return new SimpleJsonResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new SimpleJsonResult(false, e.getMessage());
		}
	}
	
	private void deleteFileDir(File file){
		if(file.exists()){
			if(file.isFile()){
				file.delete();
			}else if(file.isDirectory()){
				File[] dirFiles = file.listFiles();
				if(dirFiles != null && dirFiles.length > 0){
					for (int i = 0; i < dirFiles.length; i++) {
						deleteFileDir(dirFiles[i]);
					}
				}
				file.delete();
			}
		}
	}
	
	@SuppressWarnings("unused")
	private synchronized void exeDelete(String server, String name){
		if(server != null && !server.isEmpty() && name != null && !name.isEmpty()){
			String filePath = String.format(Const.PATH_TOMCAT_WEBAPPS, server) + name;
			if(System.getProperty("os.name").toLowerCase().contains("win")){
				exeCommand(new String[]{"del", "/f/s/q", filePath + ".war"});
				exeCommand(new String[]{"rd", "/s/q", filePath});
			}else{
				exeCommand(new String[]{"rm", "-f", filePath + ".war"});
				exeCommand(new String[]{"rm", "-rf", filePath});
			}
		}
	}
	
	private synchronized void exeCommand(String [] command){
		Process process = null;
    	try {
    		process = Runtime.getRuntime().exec(command);
    		process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			if(process != null){
				process.destroy();
			}
		}
	}

}
