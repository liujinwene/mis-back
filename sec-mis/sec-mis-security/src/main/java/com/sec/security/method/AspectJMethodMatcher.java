package com.sec.security.method;

import java.lang.reflect.Method;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;

/**
 * Methodƥ����ʵ�֣�����AspectJ PointCut���ʽƥ��Ŀ�귽����
 * 
 */
public class AspectJMethodMatcher {

	private PointcutParser pointcutParser = PointcutParser
			.getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution();

	public boolean match(String pattern, Method method) {
		pattern = "execution(" + pattern + ")";
		PointcutExpression pe = pointcutParser.parsePointcutExpression(pattern);
		ShadowMatch match = pe.matchesMethodExecution(method);
		return match.alwaysMatches();
	}

	public void setPointcutParser(PointcutParser pointcutParser) {
		this.pointcutParser = pointcutParser;
	}

	public PointcutParser getPointcutParser() {
		return pointcutParser;
	}
	
}
