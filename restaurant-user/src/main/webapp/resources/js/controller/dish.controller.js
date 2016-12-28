(function() {
    var app = angular.module("restaurantApp");
    app.controller("dishCtrl", function (dishDataSvc) {
        var self = this;
        
        dishDataSvc.getDishes()
            .then(function(data){
               self.dishes = data; 
            });

        self.selectDish = function(dishName) {
            var length = self.dishes.length;
            for(var i = 0; i < length; i++) {
                var dish = self.dishes[i];
                if (dishName === dish.dishName) {
                    self.selectedDish = dish;
                    break;
                }
            }
        };
    });
}) ();