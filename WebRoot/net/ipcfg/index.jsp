<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@ page import="org.langke.net.network.firewall.FirewallInterface" %>
<%@ page import="org.langke.net.network.firewall.XMLUseJDOM" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>ipcfg</title>    
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
<%
	String path = getServletConfig().getServletContext().getRealPath("/WEB-INF/classes");
	String client_ip = request.getParameter("client_ip");
	String opp_res = "";
	if(client_ip!=null){
		XMLUseJDOM xml = new XMLUseJDOM(path+"/firewall/data.xml");
		String old_ip = xml.getIp();
		if(old_ip.indexOf(client_ip)==-1){
			client_ip=old_ip+","+client_ip;//加上原来的IP
			xml.updateIp(client_ip)	;
			opp_res = FirewallInterface.openOraclePort(client_ip);
		}else{
			opp_res = "防火墙已经有你的IP："+client_ip;
		}
		out.println(opp_res);
	}
%>
	<form action="" method="post">
		<input type="text" name="client_ip" readonly=true value="<%=request.getRemoteAddr() %>" >
		<input type="submit" name="Submit" value="把我的IP添加到防火墙">
	</form>
  </body>
</html>
