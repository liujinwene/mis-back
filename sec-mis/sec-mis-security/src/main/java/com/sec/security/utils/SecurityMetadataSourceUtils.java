package com.sec.security.utils;

import com.sec.security.filter.FilterSecurityInterceptor;


public class SecurityMetadataSourceUtils {
	
	private SecurityMetadataSourceUtils(){}
	
	public static void refresh(){
		FilterSecurityInterceptor.refresh();
	}
}
