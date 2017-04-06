<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  String context = request.getContextPath();
context = context.equals("/")? context : context + "/";
%>    
<script type="text/javascript" src="<%=context%>3rd/jquery/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="<%=context%>3rd/jquery/jquery-ui-1.10.3.full.min.js"></script>
<script type="text/javascript" src="<%=context%>3rd/jquery/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="<%=context%>3rd/jquery/jquery.validate.min.js"></script>

<script type="text/javascript" src="<%=context%>3rd/jquery/pagination/jquery.pagination.js"></script>
<script type="text/javascript" src="<%=context%>3rd/jquery/datagrid.js"></script>
<%-- <script type="text/javascript" src="<%=context%>/3rd/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="<%=context%>/3rd/ueditor/ueditor.all.min.js"></script> --%>
<link rel="stylesheet" href="<%=context%>3rd/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="<%=context%>3rd/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="<%=context%>3rd/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="<%=context%>3rd/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="<%=context%>3rd/kindeditor/plugins/code/prettify.js"></script>

<script type="text/javascript" src="<%=context%>3rd/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=context%>3rd/bootstrap/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="<%=context%>3rd/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="<%=context%>3rd/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script> 
<script type="text/javascript" src="<%=context%>3rd/bootstrap/js/moment.min.js"></script>
<!-- ace scripts -->
<script type="text/javascript" src="<%=context%>3rd/bootstrap/js/ace-extra.min.js"></script>
<script type="text/javascript" src="<%=context%>3rd/bootstrap/js/ace-elements.min.js"></script>
<script type="text/javascript" src="<%=context%>3rd/bootstrap/js/ace.min.js"></script>
<script type="text/javascript" src="<%=context%>3rd/json2.js"></script>
<script type="text/javascript" src="<%=context%>js/ui-plugin.js"></script>
<script type="text/javascript" src="<%=context%>js/common.js"></script>
<script type="text/javascript" src="<%=context%>js/ReportQueryPanel.js"></script>
<script type="text/javascript" src="<%=context%>js/focus.js"></script>

<script type="text/javascript" src="<%=context%>3rd/layer/layer.min.js"></script>
<script type="text/javascript" src="<%=context%>3rd/layer/extend/layer.ext.js"></script>


<%-- <script type="text/javascript" src="<%=context%>3rd/ueditor/ueditor.config.js"></script> --%>
<%-- <script type="text/javascript" src="<%=context%>3rd/ueditor/ueditor.all.min.js"></script> --%>
<%-- <script type="text/javascript" src="<%=context%>3rd/ueditor/ueditor.custom.js"></script> --%>

<script type="text/javascript" src="<%=context %>3rd/jquery/fileupload/jquery.fileupload.js"></script>
<script type="text/javascript" src="<%=context %>3rd/jquery/fileupload/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="<%=context %>3rd/jquery/fileupload/jquery.fileupload-process.js"></script>
<script type="text/javascript" src="<%=context %>3rd/jquery/fileupload/jquery.fileupload-validate.js"></script>
<script type="text/javascript" src="<%=context %>3rd/jquery/fileupload/jquery.xdr-transport.js"></script>

<link rel="stylesheet" type="text/css" href="<%=context %>css/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=context %>css/themes/icon.css"/>
<script type="text/javascript" src="<%=context %>js/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=context %>js/easyui/jquery.easyui.min.js"></script>
