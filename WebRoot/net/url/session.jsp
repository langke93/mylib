<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
out.println("<br>session.getMaxInactiveInterval()");
out.println("<br>session.getMaxInactiveInterval()"+session.getMaxInactiveInterval());
out.println("<br>session.getServletContext().getMajorVersion()"+application.getMajorVersion());
out.println("<br>session.getServletContext().getMinorVersion()"+application.getMinorVersion());
out.println("<br>session.getServletContext().getServerInfo()"+application.getServerInfo());

%>