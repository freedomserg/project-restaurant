<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>City cafe</title>
    <spring:url value="/resources/css/styles.css" var="mainCss" />
    <link rel="stylesheet" href="${mainCss}">
</head>
<body>
    <div class="container">

        <header>
            <h1>City cafe</h1>
        </header>

        <nav>
            <ul>
                <li><a href="#">Main</a></li>
                <li><a href="#">Schema</a></li>
                <li><a href="#">Our team</a></li>
                <li><a href="#">Contacts</a></li>
            </ul>
        </nav>

        <article>
            <h1>City cafe</h1>
            <p>About our cafe.</p>
            <p>Info.</p>
        </article>

    </div>

    <footer>Copyright © Corp</footer>

</body>
</html>
