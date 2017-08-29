package com.auto.test.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import com.auto.test.constant.Const;
import com.auto.test.exception.BusinessException;

public class SvnUtil {
	private static Logger logger = LoggerFactory.getLogger(SvnUtil.class);
	private SVNRepository repository = null;
	private String url = null;
	
	public SvnUtil(String svnUrl) {
		super();
		this.url = svnUrl;
		init();
	}
	
	@SuppressWarnings("deprecation")
	private void init() {
		try {
			repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
			ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(Const.SVN_USERNAME, Const.SVN_PASSWORD);
			repository.setAuthenticationManager(authManager);
		} catch (SVNException e) {
			logger.error("实例化SVN服务器失败[" + e.getMessage() + "]");
			throw new BusinessException("实例化SVN服务器失败[" + e.getMessage() + "]");
		}
	}
	
	public void downloadSVNDir(String root){
		new FileUtil().deleteDir(root);
		List<String> list = new ArrayList<String>();
		listEntries(list, root, "");
		if(list != null && !list.isEmpty()){
			SVNProperties svnProperties = null;
			OutputStream output = null;
			try {
				for (String svn : list) {
					svnProperties = new SVNProperties();
					output = new FileOutputStream(new File(root + svn));
					repository.getFile(svn, -1, svnProperties, output);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(svnProperties != null){
						svnProperties.dispose();
					}
					if(output != null){
						output.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void listEntries(List<String> list, String root, String path){
		try {
			Collection<?> entries = repository.getDir(path, -1, null, (Collection<?>)null);
			Iterator<?> iterator = entries.iterator();
			while (iterator.hasNext()){
				SVNDirEntry entry = (SVNDirEntry)iterator.next();
				String name = ("".equals(path) ? "" : path + "/") + entry.getName();
				if(entry.getKind() == SVNNodeKind.DIR) {
					createDirs(root + name);
					listEntries(list, root, ("".equals(path)) ? entry.getName() : path + "/" + entry.getName());
				}else if(entry.getKind() == SVNNodeKind.FILE){
					list.add(name);
				}
			}
		} catch (SVNException e) {
			logger.error("获取SVN服务器文件列表失败[" + url + "][" + e.getMessage() + "]");
			throw new BusinessException("获取SVN服务器文件列表失败[" + url + "][" + e.getMessage() + "]");
		}
	}
	
	private void createDirs(String path){
		File f = new File(path);
		if(!f.exists() || !f.isDirectory()){
			f.mkdirs();
		}
	}

}
