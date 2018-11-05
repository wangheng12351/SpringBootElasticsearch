<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table align='center' border='1' cellspacing='0'>
    <tr>
        <td>开心就好</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>生日</td>
    </tr>
    <c:forEach items="${students}" var="s" varStatus="st">
        <tr>
            <td>${s.id}</td>
            <td>${s.name}</td>
            <td>${s.age}</td>
            <td>${s.birthday}</td>
        </tr>
    </c:forEach>
</table>