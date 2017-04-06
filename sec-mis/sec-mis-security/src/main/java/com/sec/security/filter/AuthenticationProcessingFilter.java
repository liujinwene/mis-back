package com.sec.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class AuthenticationProcessingFilter extends UsernamePasswordAuthenticationFilter {
	//
	public static final String SPRING_SECURITY_FORM_USERTYPE_KEY = "j_usertype";
	
	// ~~~~~~~~~~~~~~~~~~~~~~Field~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	
	// 认证失败url
	// 参考SimpleUrlAuthenticationFailureHandler.defaultFailureUrl
	private String defaultFailureUrl = null;

	// 是否总是调转到defaultTargetUrl
	// 参考AbstractAuthenticationTargetUrlRequestHandler.alwaysUseDefaultTargetUrl
	private boolean alwaysUseDefaultTargetUrl = false;

	// 认证成功后默认调转路径
	// 参考AbstractAuthenticationTargetUrlRequestHandler.defaultTargetUrl
	private String defaultTargetUrl = null;
	
	private boolean postOnly = true;
	
	
	private String usertypeParameter = SPRING_SECURITY_FORM_USERTYPE_KEY;
	// ~~~~~~~~~~~~~~~~~~~~~~Constructor~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	public AuthenticationProcessingFilter() {

	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		// 如果是默认验证失败处理器，设置认证失败路径
		if (defaultFailureUrl != null && getFailureHandler() instanceof SimpleUrlAuthenticationFailureHandler) {
			SimpleUrlAuthenticationFailureHandler failureHandler = (SimpleUrlAuthenticationFailureHandler) getFailureHandler();
			failureHandler.setDefaultFailureUrl(defaultFailureUrl);
		}
		// 如果是默认验证成功处理器，设置认证成功路径
		if (defaultTargetUrl != null && getSuccessHandler() instanceof SimpleUrlAuthenticationSuccessHandler) {
			SimpleUrlAuthenticationSuccessHandler successHandler = (SimpleUrlAuthenticationSuccessHandler) getSuccessHandler();
			successHandler.setDefaultTargetUrl(defaultTargetUrl);
			successHandler.setAlwaysUseDefaultTargetUrl(alwaysUseDefaultTargetUrl);
		}
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

		if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String usertype = obtainUsertype(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }
        
        if (usertype == null) {
        	usertype = "";
        }

        username = username.trim();
        
        //扩展使得能够适应多参数值的传递
        usertype = usertype.trim();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);

	}
	
	protected String obtainUsertype(HttpServletRequest request) {
		return request.getParameter(usertypeParameter);
	}
	
	
	// ~~~~~~~~~~~~~~~~~~~~~~ Accessor~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}

	public boolean isAlwaysUseDefaultTargetUrl() {
		return alwaysUseDefaultTargetUrl;
	}

	public void setAlwaysUseDefaultTargetUrl(boolean alwaysUseDefaultTargetUrl) {
		this.alwaysUseDefaultTargetUrl = alwaysUseDefaultTargetUrl;
	}

	public String getDefaultTargetUrl() {
		return defaultTargetUrl;
	}

	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}
	
	

}
