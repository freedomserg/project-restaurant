(function() {
    var app = angular.module("restaurantApp");
    app.service("dishDataSvc", function ($http) {
        var self = this;
        self.getDishes = function() {
            return $http.get("http://localhost:8080/restaurant/dishes")
                .then(function(response){
                    return response.data;
                });
        };
    });
}) ();