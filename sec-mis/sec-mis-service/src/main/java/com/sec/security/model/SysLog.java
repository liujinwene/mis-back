package com.sec.security.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("sysLog")
public class SysLog
{

    private Long operator;

    private String operateContent;

    private Date operateTime;

    private int operateType;

    private String userName;

    private String realName;

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getRealName()
    {
        return realName;
    }

    public void setRealName(String realName)
    {
        this.realName = realName;
    }

    public int getOperateType()
    {
        return operateType;
    }

    public void setOperateType(int operateType)
    {
        this.operateType = operateType;
    }

    public Long getOperator()
    {
        return operator;
    }

    public void setOperator(Long operator)
    {
        this.operator = operator;
    }

    public String getOperateContent()
    {
        return operateContent;
    }

    public void setOperateContent(String operateContent)
    {
        this.operateContent = operateContent;
    }

    public Date getOperateTime()
    {
        return operateTime;
    }

    public void setOperateTime(Date operateTime)
    {

        this.operateTime = operateTime;
    }

}
