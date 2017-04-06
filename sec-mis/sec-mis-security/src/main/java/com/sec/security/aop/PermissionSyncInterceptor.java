package com.sec.security.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;

import com.sec.security.utils.SecurityMetadataSourceUtils;

public class PermissionSyncInterceptor {
	
	private static Log log = LogFactory.getLog(PermissionSyncInterceptor.class);
	
	/**
	 *
	 * @param joinPoint
	 */
	//TODO 会拦截两次，抽时间解决
	public void after(JoinPoint joinPoint) {
		log.info("-----方法:" + joinPoint.getSignature().getName());
        log.info("permission refrsh........");
       SecurityMetadataSourceUtils.refresh();
        
    }
}
