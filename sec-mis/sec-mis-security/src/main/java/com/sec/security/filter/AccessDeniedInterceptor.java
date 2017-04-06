package com.sec.security.filter;

import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.security.access.AccessDeniedException;

public class AccessDeniedInterceptor implements ThrowsAdvice {
	
	public void afterThrowing(Method method, Object[] args, Object target, AccessDeniedException exception) {
		// System.out.println("access denied.....");
		// TODO 通过DatabaseMethodDefinitionSource拒绝访问后的处理
	}
}
