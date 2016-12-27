(function() {
    var app = angular.module("restaurantApp");
    app.controller("dishCtrl", function (dishDataSvc) {
        var self = this;

        console.log("dishCtrl is working");
        
        dishDataSvc.getDishes()
            .then(function(data){
               self.dishes = data; 
            });

        self.selectDish = function(index) {
            self.selectedDish = self.dishes[index];
        };
    });
}) ();