diamondApp.controller("BookOrderController", function BookOrderController($scope, $http, $rootScope, $timeout, $interval, DiamondService, BookOrderService) {

    var self = this;
    self.firstTimeOut = true;


    var callBookOrderService = function() {
        if(DiamondService.getCurrentDiamond()==null){
            $timeout(callBookOrderService, 150, false);
            return;
        }

        BookOrderService.getBookOrder(DiamondService.getCurrentDiamond()).then(function (data) {
            self.bookOrder = data;
            if(self.firstTimeOut) {
                self.firstTimeOut = false;
                window.setInterval(callBookOrderService, 1000);
            }
        });
    }

    callBookOrderService();
});