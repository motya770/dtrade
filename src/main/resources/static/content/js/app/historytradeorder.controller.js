diamondApp.controller("HistoryTradeOrderController", function TradeOrderController($scope, $rootScope, $http, $interval, $timeout, DiamondService, AccountService, TradeOrderService){

    var self = this;
    self.firstTimeOut = true;

    var historyOrders = function() {
        if(DiamondService.getCurrentDiamond()==null){
            $timeout(historyOrders, 150, false);
            return;
        }
        TradeOrderService.getHistoryOrders(DiamondService.getCurrentDiamond()).then(function (data) {
            self.historyTradeOrders = data;
            if(self.firstTimeOut) {
                self.firstTimeOut = false;
                console.log("Setting interval");
                window.setInterval(historyOrders, 1000);
            }
        });
    };

    console.log("START START");
    historyOrders();
});
