diamondApp.controller("HistoryTradeOrderController", function TradeOrderController($scope, $q, $rootScope, $http, $interval, $timeout, DiamondService, AccountService, TradeOrderService){

    console.log("start: " + new Date());

    var self = this;
    self.firstTimeOut = true;
    var historyInterval;

    var historyOrders = function() {
        if(DiamondService.getCurrentDiamond()==null){
            $timeout(historyOrders, 50, false);
            return;
        }
        TradeOrderService.getHistoryOrders(DiamondService.getCurrentDiamond()).then(function (data) {
            self.historyTradeOrders = data;

            console.log("end: " + new Date());
            if(self.firstTimeOut) {
                self.firstTimeOut = false;
                console.log("Setting interval");
            }

            if(historyInterval!=null){
                clearInterval(historyInterval);
            }
            historyInterval = window.setInterval(historyOrders, 1000);
        });
    };

    console.log("START START");
    historyOrders();
});
