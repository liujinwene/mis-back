package com.sec.mis.util;

import javax.servlet.http.HttpServletRequest;

public class HttpUtils {
	
	/**
	 * 返回IP地址
	 * @param request
	 * @return
	 */
	public static String ip(HttpServletRequest request){
		String ipAddress = request.getHeader("X-FORWARDED-FOR"); // proxy server
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();

		}

		// 2015 5 12 113.88.176.106, 10.8.0.1
		if (ipAddress.indexOf(",") > 0) {
			ipAddress = ipAddress.replaceAll(",", " ");
		}

		return ipAddress;
	}
}
