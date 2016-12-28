<#import "/spring.ftl" as spring />
<html ng-app="restaurantApp">
    <head>
        <title>City Cafe</title>
        <meta charset="utf-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
        <link rel="stylesheet" href="<@spring.url '/resources/css/styles.css'/>">
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.0-rc.2/angular.min.js"></script>
        <script type="text/javascript" src="<@spring.url '/resources/js/app.js'/>"></script>
    </head>

    <body>

        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">City Cafe</a>
                </div>
                <ul class="nav navbar-nav">
                    <li><a href="<@spring.url '/'/>">Home</a></li>
                    <li class="active"><a href="<@spring.url '/schema'/>">Schema</a></li>
                    <li><a href="<@spring.url '/staff'/>">Our staff</a></li>
                    <li><a href="<@spring.url '/contacts'/>">Contacts</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
                    <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                </ul>
            </div>
        </nav>

        <div class="container-fluid">
            <div class="col-md-12">
                <div class="media">
                    <div class="media-body">
                        <a href="#">
                            <img class="media-object" ng-src="<@spring.url '/resources/img/schema.gif'/>"></img>
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <div class="container-fluid">
            <#include "/WEB-INF/views/freemarker/footer.ftl">
        </div>

    </body>
</html>