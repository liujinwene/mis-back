package com.sec.security.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sec.mis.page.Page;
import com.sec.security.dao.SysLogDao;
import com.sec.security.model.SysLog;
import com.sec.security.service.SysLogService;

@Service
public class SysLogServiceImpl implements SysLogService
{

    @Resource
    private SysLogDao sysLogDao;

    @Override
    public void createSysLog(SysLog sysLog)
    {
        sysLogDao.createSysLog(sysLog);
    }

    @Override
    public Page<SysLog> findSysLogList(Map<String, Object> queryParam, int start, int limit, String order)
    {
        int count = sysLogDao.countSysLog(queryParam);
        if (count <= 0)
        {
            return Page.empty();
        }
        queryParam.put("start", start);
        queryParam.put("limit", limit);
        List<SysLog> list = sysLogDao.listSysLog(queryParam);
        return Page.create(list, count);
    }

}
