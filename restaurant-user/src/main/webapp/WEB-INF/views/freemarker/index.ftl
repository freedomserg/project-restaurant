<#assign springtags=JspTaglibs&#91;"http&#58;//www.springframework.org/tags"&#93;>
<!DOCTYPE html>
<html ng-app="restaurantApp">
    <head>
        <title>City Cafe</title>
        <meta charset="utf-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.0-rc.2/angular.min.js"></script>
        <script type="text/javascript" src="/resources/js/app.js"></script>
        <script type="text/javascript" src="/resources/js/controller/employee.controller.js"></script>
    </head>

    <body>

    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">City Cafe</a>
            </div>
            <ul class="nav navbar-nav">
                <li class="active"><a href="/restaurant/">Home</a></li>
                <li><a href="#">Schema</a></li>
                <li><a href="/restaurant/staff">Our staff</a></li>
                <li><a href="#">Contacts</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
                <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
            </ul>
        </div>
    </nav>

    <div class="container">
        <p>Home page</p>
    </div>


    </body>
</html>