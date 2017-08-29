package com.auto.test.context;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.auto.test.exception.BusinessException;

public class LogContext {
	private String name = null;
	private Process process = null;
	private BufferedReader reader = null;
	private StringBuffer logSB = new StringBuffer();
	
	public void stop(){
		try {
			if(process != null){
	    		process.destroy();
	    		process = null;
	    	}
			if(reader != null){
				reader.close();
				reader = null;
			}
			if(logSB != null){
				logSB = new StringBuffer();
			}
			name = null;
		} catch (IOException e) {
		}
	}
	
	public boolean isRun(){
		return process != null;
	}
	
	public Object read(){
		return logSB.toString();
	}
	
	public void exe(String server){
		name = server;
		String out = "/data/tomcat/" + server + "/logs/catalina.out";
		File file = new File(out);
		if(file.exists() && file.isFile()){
			String [] command = new String[]{"tail", "-f", out};
			final ProcessBuilder pb = new ProcessBuilder(command).redirectErrorStream(true);
			final Thread exe = new Thread(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                    process = pb.start();
	                    InputStream is = process.getInputStream();
	                    new StreamCleaner(is).start();
	                    process.waitFor();
	                } catch (Exception e) {
	                }
	            }
	        });
			exe.setName("Log-catalina-" + exe.getId());
			exe.start();
		}else{
			throw new BusinessException("文件不存在[" + out + "]");
		}
	}
	
	class StreamCleaner extends Thread {
        private InputStream inputStream;
        public StreamCleaner(InputStream inputStream) {
            super();
            this.inputStream = inputStream;
            this.setDaemon(true);
            this.setName("Log-StreamCleaner-" + this.getId());
        }

        @Override
        public void run() {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            try {
                do {
                    line = reader.readLine();
                    logSB.append(line + "\r\n");
                } while (line != null);
            } catch (IOException e) {
            }
        }
    }

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Process getProcess() {
		return process;
	}
	public void setProcess(Process process) {
		this.process = process;
	}
	public StringBuffer getLogSB() {
		return logSB;
	}
	public void setLogSB(StringBuffer logSB) {
		this.logSB = logSB;
	}
	public BufferedReader getReader() {
		return reader;
	}
	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

}
