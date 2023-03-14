<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Todo List</title>
</head>
<body>
<h1>List Page</h1>
<h2>${loginInfo}</h2>
<h2>${loginInfo.mname}</h2>

<ul>
    <c:forEach var="dto" items="${dtoList}">
        <li><span><a href="/todo/read?tno=${dto.tno}">${dto.tno}</a> </span>
            <span>${dto.title}</span>
            <span>${dto.dueDate}</span>
            <span>${dto.finished? "DONE" : "NOT YET"}</span>

        </li>
    </c:forEach>
</ul>
<form action="/logout" method="post">
    <button>LOGOUT</button>

</form>

</body>
</html>
