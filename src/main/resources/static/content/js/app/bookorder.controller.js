diamondApp.controller("BookOrderController", function BookOrderController($scope, $http, $rootScope, $timeout, $interval, DiamondService, BookOrderService) {

    var self = this;
    self.firstTimeOut = true;
    self.spread = 0;


    var callBookOrderService = function() {
        if(DiamondService.getCurrentDiamond()==null){
            $timeout(callBookOrderService, 150, false);
            return;
        }

        BookOrderService.getBookOrder(DiamondService.getCurrentDiamond()).then(function (data) {
            self.bookOrder = data;

            if(self.bookOrder.buyOrders.length > 0 && self.bookOrder.sellOrders.length > 0) {
                self.spread = self.bookOrder.sellOrders[0].price - self.bookOrder.buyOrders[self.bookOrder.buyOrders.length - 1].price;
                //console.log("spread: " + self.spread  + " " + self.bookOrder.buyOrders[self.bookOrder.buyOrders.length - 1].price  + " " + self.bookOrder.sellOrders[0].price);
            }
            if(self.firstTimeOut) {
                self.firstTimeOut = false;
                window.setInterval(callBookOrderService, 1000);
            }
        });
    }

    callBookOrderService();
});