(function() {
    var app = angular.module("restaurantApp");
    app.service("waiterDataSvc", function($http){
        var self = this;
        self.getWaiters = function() {
            return $http.get("http://localhost:8080/restaurant/waiter")
                .then(function(response){
                return response.data;
            });
        };
    });

})();