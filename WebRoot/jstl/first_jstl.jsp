<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=gb2312" language="java" %>
<c:set var="userName" value="hellking"/>
<c:set value="16" var="age"/>
��ӭ����<c:out value="${userName}"/><hr>
<c:forEach var="i" begin="1" end="5">
<font size=${i}>${i}</font>
<br>
</c:forEach>

<c:if test="${age<18}">
 �Բ�����������С�����ܷ��������ҳ��
</c:if>
<br>
</body>
</html>

