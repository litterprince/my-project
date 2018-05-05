package com.example.dbmt.tool;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.regex.Pattern;

public class FilePathUtil {
	private static Pattern filePattern = Pattern.compile("[\\\\/:*?\"<>|]");
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");

	public static String getRealFilePath(String path) {
		return path.replace("/", FILE_SEPARATOR).replace("\\", FILE_SEPARATOR);
	}

	public static String getHttpURLPath(String path) {
		return path.replace("\\", "/");
	}

	// 获取文件绝对路径
	public static String getServerPath(File file) {
		String path = "";
		try {
			path = file.getCanonicalPath();
		} catch (Exception e) {
			System.out.println(e);
		}
		return path;
	}

	// 过滤文件非法字符串
	public static String filenameFilter(String str) {
		return str == null ? null : filePattern.matcher(str).replaceAll("");
	}

	public static String getHttpRootPath(HttpServletRequest request){
		String getServerName = request.getServerName();
		String port = String.valueOf(request.getLocalPort());
		String context = request.getContextPath();
		return FilePathUtil.getHttpURLPath("http://"+getServerName+":"+port+context+ "/");
	}
}
