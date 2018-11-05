<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table align='center' border='1' cellspacing='0'>
    <tr>
        <td>通道ID</td>
        <td>手机号</td>
        <td>特服号</td>
        <td>发送时间</td>
        <td>发送内容</td>
    </tr>
    <c:forEach items="${HistoryMTInfo}" var="s" varStatus="st">
        <tr>
            <td>${s.channelId}</td>
            <td>${s.number}</td>
            <td>${s.serviceCode}</td>
            <td>${s.createTime}</td>
            <td>${s.smsContent}</td>
        </tr>
    </c:forEach>
</table>