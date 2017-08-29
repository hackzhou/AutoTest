package com.auto.test.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	
	public String newMaxFileName(String max){
		if("0".equals(max)){
			return "1000";
		}else{
			return String.valueOf(Integer.parseInt(max) + 1);
		}
	}
	
	public String maxFileNameByDir(String path){
		int def = 0;
		List<String> list = getAllDirName(path);
		if(list != null && !list.isEmpty()){
			for (String s : list) {
				try {
					int temp = Integer.parseInt(s);
					if(temp > def){
						def = temp;
					}
				} catch (Exception e) {
				}
			}
		}
		return String.valueOf(def);
	}
	
	public List<String> getAllDirName(String path){
		List<String> list = null;
		File dir = new File(path);
		if(dir.exists() && dir.isDirectory()){
			list = new ArrayList<String>();
			for (File f : dir.listFiles()) {
				if(f.exists() && f.isDirectory()){
					list.add(f.getName());
				}
			}
		}
		return list;
	}
	
	public boolean deleteDir(String path){
		File dir = new File(path);
		return deleteDir(dir);
	}
	
	public boolean deleteDir(File dir){
		try {
			if(dir.exists()){
				if(dir.isDirectory()){
					for (String file : dir.list()) {
						if(!deleteDir(new File(dir, file))){
							return false;
						}
					}
				}
				return dir.delete();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void createDir(String path){
		File dir = new File(path);
		if(!dir.exists() || !dir.isDirectory()){
			dir.mkdirs();
		}
	}

}
