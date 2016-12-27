(function() {

    var app = angular.module("restaurantApp");
    app.controller("waiterCtrl", waiterCtrl);

    function waiterCtrl(waiterDataSvc) {
        var self = this;

        waiterDataSvc.getWaiters()
            .then(function(data){
                self.waiters = data;
            });

        this.selectWaiter = function(index) {
            this.selectedWaiter = this.waiters[index];
        };
    }
})();