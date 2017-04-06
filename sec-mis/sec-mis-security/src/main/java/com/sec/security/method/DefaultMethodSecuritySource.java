package com.sec.security.method;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.method.AbstractFallbackMethodSecurityMetadataSource;


/**
 * �� <code>MethodMapFactoryBean</code> �л�ȡ�����õķ�������Ҫ��Ȩ�ޱ�ʶ��
 * 
 */
public class DefaultMethodSecuritySource extends AbstractFallbackMethodSecurityMetadataSource {

	private AspectJMethodMatcher methodMatcher;
	private LinkedHashMap<String, Collection<ConfigAttribute>> methodMap = new LinkedHashMap<String, Collection<ConfigAttribute>>();

	public DefaultMethodSecuritySource(AspectJMethodMatcher methodMatcher, LinkedHashMap<String, Collection<ConfigAttribute>> methodMap) {
		this.methodMatcher = methodMatcher;
		this.methodMap = methodMap;
	}

	@Override
	protected Collection<ConfigAttribute> findAttributes(Method method, Class<?> targetClass) {
		Set<Entry<String, Collection<ConfigAttribute>>> set = getMethodMap().entrySet();
		for (Entry<String, Collection<ConfigAttribute>> entry : set) {
			String pattern = entry.getKey();
			boolean matched = getMethodMatcher().match(pattern, method);
			if (matched) {
				return entry.getValue();
			}
		}
		return null;
	}

	@Override
	protected Collection<ConfigAttribute> findAttributes(Class<?> clazz) {
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

		for (Collection<ConfigAttribute> attributeList : methodMap.values()) {
			allAttributes.addAll(attributeList);
		}

		return allAttributes;
	}

	public AspectJMethodMatcher getMethodMatcher() {
		return methodMatcher;
	}

	public void setMethodMatcher(AspectJMethodMatcher methodMatcher) {
		this.methodMatcher = methodMatcher;
	}

	public LinkedHashMap<String, Collection<ConfigAttribute>> getMethodMap() {
		return methodMap;
	}

	public void setMethodMap(LinkedHashMap<String, Collection<ConfigAttribute>> methodMap) {
		this.methodMap = methodMap;
	}

}
