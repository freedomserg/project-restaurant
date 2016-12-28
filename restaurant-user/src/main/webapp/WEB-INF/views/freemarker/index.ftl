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
        <script type="text/javascript" src="<@spring.url '/resources/js/service/dishData.service.js'/>"></script>
        <script type="text/javascript" src="<@spring.url '/resources/js/controller/dish.controller.js'/>"></script>
    </head>

    <body>

        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">City Cafe</a>
                </div>
                <ul class="nav navbar-nav">
                    <li class="active"><a href="/restaurant/">Home</a></li>
                    <li><a href="/restaurant/schema">Schema</a></li>
                    <li><a href="/restaurant/staff">Our staff</a></li>
                    <li><a href="/restaurant/contacts">Contacts</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
                    <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                </ul>
            </div>
        </nav>

        <div class="container-fluid" ng-controller="dishCtrl as ctrl">

            <div class="page-header">
                <h4>Menu</h4>
            </div>

            <div class="row">
                <div class="col-md-4">
                    <ul class="list-group">
                        <li class="list-group-item" ng-repeat="dish in ctrl.dishes" ng-click="ctrl.selectDish($index)">
                            <div>{{dish.dishName}}</div>
                            <ul>
                                <li>price: {{dish.price}}$</li>
                                <li>weight: {{dish.weight}}g</li>
                            </ul>
                        </li>
                    </ul>
                </div>

                <div class="col-md-6">
                    <div class="media">
                        <div class="media-left">
                            <a href="#">
                                <img ng-src="<@spring.url '{{ctrl.selectedDish.picture}}'/>"></img>
                            </a>
                        </div>
                        <div class="media-body" ng-show="ctrl.selectedDish">
                            <p>
                                <span>Dish:</span>
                                <span>{{ctrl.selectedDish.dishName}}</span>
                            </p>
                            <p>Category: {{ctrl.selectedDish.category.categoryName}}</p>
                            <p>Price: {{ctrl.selectedDish.price}}$</p>
                            <p>Weight: {{ctrl.selectedDish.weight}}g</p>
                            <div>
                                <p>Ingredients:</p>
                                <ul class="list-group">
                                    <li class="list-group-item" ng-repeat="unit in ctrl.selectedDish.units">
                                        <span>{{unit.ingredient.ingredientName}}</span>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="container-fluid">
            <#include "/WEB-INF/views/freemarker/footer.ftl">
        </div>

    </body>
</html>