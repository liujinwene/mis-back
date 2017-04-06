package com.sec.security.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.sec.security.model.SecAcl;
import com.sec.security.model.SecMenu;
import com.sec.security.model.SecRole;
import com.sec.security.service.MenuService;

/**
 * 自定义过滤器，对URL资源进行安全控制.
 * <p>
 * 此类负责对URL请求进行安全验证，包含一个静态的权限资源<code>ObjectDefinitionSource</code>（ {@link FilterInvocationDefinitionSource}类型）.
 * </p>
 * <p>
 * 参照{@link AbstractSecurityInterceptor}了解详细的工作流程.
 * </p>
 * 
 */
public class FilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
	private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";
	private static final String AUTHORITY_TYPE = "URL";
	private boolean observeOncePerRequest = true;
	private static SecurityMetadataSource securityMetadataSource;

	@Resource
	private MenuService menuService;

	@Override
	public Class<?> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		if (securityMetadataSource == null) {

			// 从数据库或者配置文件加载所有的URL资源角色授权
			// 现在的实现是简单的例子
			LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();

			List<SecAcl> acls = menuService.listAcls(true);

			for (SecAcl acl : acls) {
				RequestMatcher urlMatcher = new AntPathRequestMatcher(acl.getPattern());
				Collection<ConfigAttribute> attributes = new HashSet<ConfigAttribute>();
				for (SecRole role : acl.getRoles()) {
					attributes.add(new SecurityConfig(role.getCode()));
				}
				requestMap.put(urlMatcher, attributes);
			}
			List<SecMenu> menus = menuService.findMenus(true);
			for(SecMenu menu:menus){
				RequestMatcher urlMatcher = new AntPathRequestMatcher(menu.getLocation());
				Collection<ConfigAttribute> attributes = new HashSet<ConfigAttribute>();
				for(SecRole role:menu.getRoles()){
					attributes.add(new SecurityConfig(role.getCode()));
				}
				requestMap.put(urlMatcher, attributes);
			}
			
			Collection<ConfigAttribute> attributes = new HashSet<ConfigAttribute>();
			attributes.add(new SecurityConfig(SecRole.ROLE_DEFAULT) );
			requestMap.put(new AntPathRequestMatcher("/**/*.do"), attributes);
			
			securityMetadataSource = new DefaultFilterInvocationSecurityMetadataSource(requestMap);
		}
		return securityMetadataSource;
	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		invoke(fi);

	}

	public void invoke(FilterInvocation fi) throws IOException, ServletException {
		if ((fi.getRequest() != null) && (fi.getRequest().getAttribute(FILTER_APPLIED) != null) && observeOncePerRequest) {
			// filter already applied to this request and user wants us to
			// observce
			// once-per-request handling, so don't re-do security checking
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} else {
			// first time this request being called, so perform security
			// checking
			if (fi.getRequest() != null) {
				fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
			}

			InterceptorStatusToken token = super.beforeInvocation(fi);

			try {
				fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
			} finally {
				super.afterInvocation(token, null);
			}
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}
	
	public static void refresh() {
		securityMetadataSource = null;
	}

}
