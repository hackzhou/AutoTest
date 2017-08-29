package com.auto.test.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IoUtil {
	
	public static void main(String[] args) {
		IoUtil io = new IoUtil();
		io.replaceFile("D:\\test\\index.html", "14242", "12345");
	}
	
	public void replaceFile(String path, String t, String tn){
		File file = new File(path);
		if(file.exists() && file.isFile()){
			String source = readTextFile(file);
			if(source != null){
				writeTextFile(path, source.replace(t, tn));
			}
		}
	}
	
	public String readTextFile(File file){
		BufferedReader reader = null;
		try {
			StringBuffer sb = new StringBuffer();
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				sb.append(tempString + "\r\n");
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(reader != null){
					reader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	public boolean writeTextFile(String path, String source){
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(path));
			bw.write(source);
			bw.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if(bw != null){
					bw.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
