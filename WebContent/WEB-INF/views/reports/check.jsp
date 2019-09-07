<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${check_flag_count < 1}">
        <form method="POST" action="<c:url value='/checks/create' />">
            <input type="hidden" name="id" value="${report.id}" />
            <input type="hidden" name="_token" value="${_token}" />
            <button type="submit">チェックする</button>
        </form>
    </c:when>
    <c:otherwise>
        <form method="POST" action="<c:url value='/checks/destroy' />">
            <input type="hidden" name="id" value="${report.id}" />
            <input type="hidden" name="_token" value="${_token}" />
            <button type="submit">チェックを外す</button>
        </form>
    </c:otherwise>
</c:choose>
チェック数(${checks_count}) 件<br />
チェック者一覧<br />
<c:forEach var="check" items="${checks}">
[<c:out value="${check.employee.name}" />]&nbsp;
</c:forEach>