package com.sec.web.security;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sec.mis.page.Page;
import com.sec.security.model.SysLog;
import com.sec.security.service.SysLogService;
import com.sec.web.vo.SearchParam;

@Controller
@RequestMapping("/syslog")
public class LogController
{

    private static final String FIRST = "security/log/list";

    @Resource
    private SysLogService sysLogService;

    @RequestMapping("/list")
    public String list()
    {
        return FIRST;
    }

    @RequestMapping("/list-data")
    @ResponseBody
    public Page<SysLog> findListData(SearchParam sp)
    {
        Map<String, Object> param = this.genSysLogSearchParam(sp);
        int start = sp.getStart();
        int limit = sp.getLimit();
        Page<SysLog> page = sysLogService.findSysLogList(param, start, limit, null);
        return page;
    }

    /**
     * 查询和导出 条件
     * 
     * @param sp
     * @return
     */
    public Map<String, Object> genSysLogSearchParam(SearchParam sp)
    {
        Map<String, Object> param = sp.getSp();
        return param;
    }

}
