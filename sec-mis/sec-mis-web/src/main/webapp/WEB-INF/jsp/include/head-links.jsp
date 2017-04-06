<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  String context = request.getContextPath();
context = context.equals("/")? context : context + "/";
%>    
<link rel="stylesheet" href="<%=context%>3rd/bootstrap/css/bootstrap.min.css"/>
<link rel="stylesheet" href="<%=context%>3rd/bootstrap/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=context%>3rd/jquery/jquery-ui-1.10.3.full.min.css" />
<link rel="stylesheet" href="<%=context%>3rd/bootstrap/css/datepicker.css" />
<link rel="stylesheet" href="<%=context%>3rd/bootstrap/css/bootstrap-datetimepicker.min.css" />
<%-- <link rel="stylesheet" href="<%=context%>/3rd/jquery/pagination/jquery.pagination.css" /> --%>

<link rel="stylesheet" href="<%=context%>3rd/bootstrap/css/ace.min.css" />
<link rel="stylesheet" href="<%=context%>3rd/bootstrap/css/ace-rtl.min.css" />
<link rel="stylesheet" href="<%=context%>3rd/bootstrap/css/ace-skins.min.css" />
<link rel="stylesheet" href="<%=context%>css/project.css" />