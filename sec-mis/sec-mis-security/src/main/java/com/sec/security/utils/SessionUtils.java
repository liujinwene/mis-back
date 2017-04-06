package com.sec.security.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sec.security.model.SecUser;

public class SessionUtils {
	public static SecUser getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null) return null;
		return (SecUser) auth.getPrincipal();
	}
}
