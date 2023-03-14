<%--
  Created by IntelliJ IDEA.
  User: a
  Date: 2023-03-13
  Time: 오후 3:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login.jsp</title>
</head>
<body>
<c:if test="{param.result == 'error'}">
    <h1>로그인 에러</h1>
</c:if>
<form action="/login" method="post">
    <input type="text" name="mid">
    <input type="text" name="mpw">
    <input type="checkbox" name="auto">기억하기
    <button type="submit">LOGIN<</button>
</form>
</body>
</html>
