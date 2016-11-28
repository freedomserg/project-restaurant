<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>City cafe</title>
</head>
<body>
<table style="align-items: center">
    <tr>
        <th>First name</th>
        <th>Position</th>
        <th>Salary</th>
    </tr>
    <c:forEach items="${employee}" var="employee">
        <tr>
            <td>${employee.name}</td>
            <td>${employee.position}</td>
            <td>${employee.salary}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
