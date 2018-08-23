<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ page import="javazoom.upload.*"%>
<%@ page import="org.langke.util.file.ExcelToObj"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

if (MultipartFormDataRequest.isMultipartFormData(request)) {
	MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request);
	Hashtable files = mrequest.getFiles();	
	if ((files != null) || (!files.isEmpty())) {
		UploadFile file = (UploadFile) files.get("uploadfile");
		org.langke.util.file.ExcelToObj excelToCachedRowSet = new org.langke.util.file.ExcelToObj();
		java.util.List list = excelToCachedRowSet.getList(file.getInpuStream()); 
		Iterator iterat = list.iterator();
		while(iterat.hasNext()){
	System.out.println(iterat.next());
		}
	}
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>My JSP 'excel_upload.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    This is my JSP page. <br>
  </body>
</html>
