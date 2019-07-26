package com.maven.es.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Properties;

public class PubTools {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public static int getImgType(byte[] imgBytes) {
		if (imgBytes == null || imgBytes.length < 7) {
			return -1;
		}
		if ((imgBytes[0] == -1) && (imgBytes[1] == -40) && (imgBytes[2] == -1)) {
			return 1;
		}
		if ((imgBytes[1] == 80) && (imgBytes[2] == 78) && (imgBytes[3] == 71)) {
			return 0;
		}
		if ((imgBytes[0] == 66) && (imgBytes[1] == 77)) {
			return 3;
		}
		if ((imgBytes[0] == 71) && (imgBytes[1] == 73) && (imgBytes[2] == 70)
				&& (imgBytes[3] == 56)
				&& (imgBytes[4] == 55 || imgBytes[4] == 57)
				&& (imgBytes[5] == 97)) {
			return 2;
		}
		return -1;
	}

	public static String getImgTypeString(byte[] imgBytes) {
		int type = getImgType(imgBytes);
		switch (type) {
		case 0:
			return ".png";
		case 1:
			return ".jpeg";
		case 2:
			return ".gif";
		case 3:
			return ".bmp";
		default:
			return "unkown type:" + type;
		}
	}

	public static boolean isNotBlank(String str) {
		return str != null && str.length() > 0;
	}

	public static String bytesToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String md5Encode(String origin) {
		try {
			String res = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			return bytesToHexString(md.digest(res.getBytes("utf-8")));
		} catch (Exception ex) {
			return null;
		}
	}

	public static int ipToInt(String strIp) {
		int[] ip = new int[4];
		int position1 = strIp.indexOf(".");
		int position2 = strIp.indexOf(".", position1 + 1);
		int position3 = strIp.indexOf(".", position2 + 1);
		ip[0] = Integer.parseInt(strIp.substring(0, position1));
		ip[1] = Integer.parseInt(strIp.substring(position1 + 1, position2));
		ip[2] = Integer.parseInt(strIp.substring(position2 + 1, position3));
		ip[3] = Integer.parseInt(strIp.substring(position3 + 1));
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
	}

	public static String intToIP(int intIp) {
		StringBuffer sb = new StringBuffer("");
		sb.append(String.valueOf((intIp >>> 24)));
		sb.append(".");
		sb.append(String.valueOf((intIp & 0x00FFFFFF) >>> 16));
		sb.append(".");
		sb.append(String.valueOf((intIp & 0x0000FFFF) >>> 8));
		sb.append(".");
		sb.append(String.valueOf((intIp & 0x000000FF)));
		return sb.toString();
	}

	public static Properties loadCfgFromFile(String path) throws IOException {
		Properties cfg = new Properties();
		Reader is = new FileReader(path);
		cfg.load(is);
		is.close();
		return cfg;
	}

	/**
	 * @param pwd
	 * @return
	 */
	public static String decodePwd(String pwd) {
		byte[] src = pwd.getBytes(Charset.forName("utf-8"));
		for (int i = 0; i < src.length; i++) {
			src[i] = (byte) (src[i] ^ 15);
		}
		return new String(src);
	}

	/**
	 * @param pwd
	 * @return
	 */
	public static String encodePwd(String pwd) {
		byte[] src = pwd.getBytes(Charset.forName("utf-8"));
		for (int i = 0; i < src.length; i++) {
			src[i] = (byte) (src[i] ^ 15);
		}
		return new String(src);
	}
}
