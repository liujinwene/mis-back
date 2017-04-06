package com.sec.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.filter.GenericFilterBean;

public class LogoutProcessingFilter extends GenericFilterBean {
	private String filterProcessesUrl = "/j_spring_security_logout";
	private LogoutFilter logoutFilter = null;

	public LogoutProcessingFilter(String logoutSuccessUrl, String deleteCookies) {

		logoutFilter = new LogoutFilter(logoutSuccessUrl, getLogoutHandlers(deleteCookies));
	}

	public LogoutProcessingFilter(String logoutSuccessUrl) {
		this(logoutSuccessUrl, null);
	}

	public LogoutProcessingFilter(LogoutSuccessHandler logoutSuccessHandler, String deleteCookies) {
		logoutFilter = new LogoutFilter(logoutSuccessHandler, getLogoutHandlers(deleteCookies));
	}

	public LogoutProcessingFilter(LogoutSuccessHandler logoutSuccessHandler) {
		this(logoutSuccessHandler, null);
	}

	protected LogoutHandler[] getLogoutHandlers(String deleteCookies) {
		List<LogoutHandler> handlers = new ArrayList<LogoutHandler>();
		handlers.add(new SecurityContextLogoutHandler());
		if (deleteCookies != null) {
			String[] cookiesToClear = deleteCookies.split(",");
			for (int i = 0; i < cookiesToClear.length; i++) {
				cookiesToClear[i] = cookiesToClear[i].trim();
			}
			handlers.add(new CookieClearingLogoutHandler(cookiesToClear));
		}
		return handlers.toArray(new LogoutHandler[0]);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		logoutFilter.setFilterProcessesUrl(filterProcessesUrl);
		logoutFilter.doFilter(request, response, chain);
	}

	public String getFilterProcessesUrl() {
		return filterProcessesUrl;
	}

	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}

}
