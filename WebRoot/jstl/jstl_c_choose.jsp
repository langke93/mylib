<%@ page import="java.net.URLEncoder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
test
<%
 long total = 0;
 request.setAttribute("total",total);
%>
<c:if test="${total>0}">
                  <fmt:parseNumber var="totalCountRatio" integerOnly="true" value="${10}" />
</c:if>
<c:if test="${total<1}">
                  <fmt:parseNumber var="totalCountRatio" integerOnly="true" value="${0}" />
</c:if>

 <c:if test="${total >=-50}">class='td-value red-color'  </c:if>
 <c:if test="${(total/1000*100) <= 10 && total/1000 < 100}">class='td-value red-yello'  </c:if>

  <c:choose>
                            <c:when test="${totalCountRatio == 0}">
                                 class='td-value red-color'
                            </c:when>
  </c:choose>
<c:if test="${empty totl}">
  empty test
</c:if>
${test}