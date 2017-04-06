package com.sec.security.service;

import java.util.Map;

import com.sec.mis.page.Page;
import com.sec.security.model.SysLog;

public interface SysLogService
{
    void createSysLog(SysLog sysLog);
    
    Page<SysLog> findSysLogList(Map<String, Object> queryParam, int start, int limit, String order);
}
