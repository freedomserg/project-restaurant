<#import "/spring.ftl" as spring />
<html ng-app="restaurantApp">
    <head>
        <title>City Cafe</title>
        <meta charset="utf-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
              integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
              integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
        <link rel="stylesheet" href="<@spring.url '/resources/css/styles.css'/>">
        <script type="text/javascript"
                src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.0-rc.2/angular.min.js"></script>
        <script type="text/javascript" src="<@spring.url '/resources/js/app.js'/>"></script>
        <script type="text/javascript" src="<@spring.url '/resources/js/controller/waiter.controller.js'/>"></script>
        <script type="text/javascript" src="<@spring.url '/resources/js/service/waiterData.service.js'/>"></script>
    </head>

    <body>

        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">City Cafe</a>
                </div>
                <ul class="nav navbar-nav">
                    <li><a href="/restaurant/">Home</a></li>
                    <li><a href="/restaurant/schema">Schema</a></li>
                    <li class="active"><a href="/restaurant/staff">Our staff</a></li>
                    <li><a href="/restaurant/contacts">Contacts</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
                    <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                </ul>
            </div>
        </nav>

        <div class="container" ng-controller="waiterCtrl as ctrl">
            <div class="row">
                <div class="col-md-6">
                    <ul class="list-group">
                        <li class="list-group-item" ng-repeat="waiter in ctrl.waiters" ng-click="ctrl.selectWaiter($index)">
                            <span>{{waiter.name}}</span>
                        </li>
                    </ul>
                </div>

                <div class="col-md-6">
                    <div class="media">
                        <div class="media-left">
                            <a href="#">
                                <img ng-src="{{ctrl.selectedWaiter.picture}}"></img>
                            </a>
                        </div>
                        <div class="media-body" ng-show="ctrl.selectedWaiter">
                            <p>
                                <span>Name:</span>
                                <span>{{ctrl.selectedWaiter.name}}</span>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>


    </body>
</html>