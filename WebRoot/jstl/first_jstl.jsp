<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=gb2312" language="java" %>
<c:set var="userName" value="hellking"/>
<c:set value="16" var="age"/>
欢迎您，<c:out value="${userName}"/><hr>
<c:forEach var="i" begin="1" end="5">
<font size=${i}>${i}</font>
<br>
</c:forEach>

<c:if test="${age<18}">
 对不起，你的年龄过小，不能访问这个网页◎！
</c:if>
<br>
</body>
</html>

