<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>ʹ�ñ�׼��ǩ��</title>
</head>
<body bgcolor="#FFFFFF">

<h3>�򵥵�������ǩ��ʹ��</h3>
<c:set var="userName" value="hellking" scope="session"/>
<h4>if tag:</h4>
<c:if test="${sessionScope.userName== 'hellking'}">
    ${sessionScope.userName}<br>
  </c:if>
</body>
</html>
