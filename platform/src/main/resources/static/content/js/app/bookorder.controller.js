diamondApp.controller("BookOrderController", function BookOrderController($scope, $http, $rootScope, $timeout, $interval, DiamondService, BookOrderService) {

    var self = this;
    //self.firstTimeOut = true;
    self.spread = 0.00;

    var callBookOrderService = function() {
        if(DiamondService.getCurrentDiamond()==null){
            $timeout(callBookOrderService, 150, false);
            return;
        }

        BookOrderService.getBookOrder(DiamondService.getCurrentDiamond()).then(function (data) {
            self.bookOrder = data;

            // sconsole.log("spread: " + self.spread  + " " + self.bookOrder.buyOrders[self.bookOrder.buyOrders.length - 1].price  + " " + self.bookOrder.sellOrders[0].price);

            if(self.bookOrder == null){
                return;
            }

            if(self.bookOrder.buyOrders == null || self.bookOrder.sellOrders==null){

                self.spread = 0.00;

            }else if (self.bookOrder.buyOrders.length > 0 && self.bookOrder.sellOrders.length > 0) {
                self.spread = self.bookOrder.sellOrders[self.bookOrder.sellOrders.length - 1].price -
                    self.bookOrder.buyOrders[0].price;
               // console.log("calc")
               // console.log("spread: " + self.spread  + " " + self.bookOrder.buyOrders[self.bookOrder.buyOrders.length - 1].price  + " " + self.bookOrder.sellOrders[0].price);
            } else {
                self.spread = 0.00;
            }

           // console.log("spd: " + (self.bookOrder.sellOrders[self.bookOrder.sellOrders.length - 1].price - self.bookOrder.buyOrders[0].price) + " " + self.spread);

            // if(self.firstTimeOut) {
            //     self.firstTimeOut = false;
            //     window.setInterval(callBookOrderService, 1000);
            // }
        });
    }


    var promise = $interval(callBookOrderService, 1000);

    $scope.$on('$destroy', function(){
        if (angular.isDefined(promise)) {
            $interval.cancel(promise);
            promise = undefined;
        }
    });
});