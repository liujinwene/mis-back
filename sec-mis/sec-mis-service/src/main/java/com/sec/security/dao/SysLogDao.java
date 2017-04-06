package com.sec.security.dao;

import java.util.List;
import java.util.Map;

import com.sec.mis.mybatis.MybatisDao;
import com.sec.security.model.SysLog;

@MybatisDao
public interface SysLogDao
{

    void createSysLog(SysLog sysLog);

    List<SysLog> listSysLog(Map<String, Object> query);

    int countSysLog(Map<String, Object> query);

}
