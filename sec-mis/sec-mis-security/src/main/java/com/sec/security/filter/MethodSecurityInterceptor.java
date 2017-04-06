package com.sec.security.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.access.method.MethodSecurityMetadataSource;

import com.sec.security.method.AspectJMethodMatcher;
import com.sec.security.method.DefaultMethodSecuritySource;

public class MethodSecurityInterceptor extends AbstractSecurityInterceptor implements MethodInterceptor {
	// ~ Instance fields
	// ================================================================================================

	private MethodSecurityMetadataSource securityMetadataSource;
	private static final String AUTHORITY_TYPE = "METHOD";

	public Class<?> getSecureObjectClass() {
		return MethodInvocation.class;
	}

	public MethodSecurityMetadataSource getSecurityMetadataSource() {
		return securityMetadataSource;
	}

	/**
	 * This method should be used to enforce security on a <code>MethodInvocation</code>.
	 * 
	 * @param mi
	 *            The method being invoked which requires a security decision
	 * 
	 * @return The returned value from the method invocation
	 * 
	 * @throws Throwable
	 *             if any error occurs
	 */
	public Object invoke(MethodInvocation mi) throws Throwable {
		Object result = null;
		InterceptorStatusToken token = super.beforeInvocation(mi);

		try {
			result = mi.proceed();
		} finally {
			result = super.afterInvocation(token, result);
		}

		return result;
	}

	/**
	 * 初始化MethodDefinitionSource对象，为Method拦截器提供验证访问。
	 * 
	 */
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		if (securityMetadataSource == null) {
			// TODO:从配置文件或者数据库中读取角色可以访问的资源
			// 现在的实现是简单的例子
			LinkedHashMap<String, Collection<ConfigAttribute>> methodMap = new LinkedHashMap<String, Collection<ConfigAttribute>>();

			List<ConfigAttribute> attributes = new ArrayList<ConfigAttribute>();
			//attributes.add(new SecurityConfig("ROLE_ADMIN"));

			// methodMap.put("com.xyz.ebuy.service.test.TestUserService.get", attributes);
			// securityMetadataSource = new MapBasedMethodSecurityMetadataSource(methodMap);

			//methodMap.put("public * com.xyz.ebuy.service.test.TestUserService.get(..)", attributes);
			securityMetadataSource = new DefaultMethodSecuritySource(new AspectJMethodMatcher(), methodMap);

		}
		return securityMetadataSource;
	}

}
