<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="java.util.*"%>
<form action="online.jsp" method="post">
�û�����
<input type="text" name="name">
<input type="submit" value="��½">
<a href="logout.jsp">ע��</a>
</form>
<!-- ��session����������û��� -->
<%
	if(request.getParameter("name")!=null)
	{
		session.setAttribute("session_user_name",request.getParameter("name")+","+request.getRemoteAddr()) ;
	}
%>
<h2>������Ա</h2>
<hr>
<%
	List l = (List)application.getAttribute("application_user_info") ;
	Iterator iter = l.iterator() ;
	while(iter.hasNext())
	{
%>
		<li><%=iter.next()%>
<%
	}
%>