diamondApp.controller("TradeOrderController", function TradeOrderController($scope, $rootScope, $http, $interval, $timeout, DiamondService, AccountService, TradeOrderService){

    var self = this;
    self.firstTimeOut = true;

    $scope.cancelTradeOrder = function(tradeOrder) {
        $http.post("/trade-order/cancel", tradeOrder, null).then(function (response) {
            console.log("tradeOrder canceled  " + response.data);
            TradeOrderService.addHistoryOrder(response.data);
        });
    }

    $scope.getPreviousLiveOrders = function (pageNumber) {
        TradeOrderService.getLiveOrders(pageNumber + 1).then(function (data) {
            self.liveTradeOrders = data;
        });
    }
    
    TradeOrderService.getLiveOrders().then(function (data) {
        self.liveTradeOrders = data;
    });

    var poller = function() {
        if(DiamondService.getCurrentDiamond()==null){
            $timeout(poller, 150, false);
            return;
        }
        TradeOrderService.getHistoryOrders(DiamondService.getCurrentDiamond()).then(function (data) {
            self.historyTradeOrders = data;
            if(self.firstTimeOut) {
                self.firstTimeOut = false;
                window.setInterval(poller, 1000);
               // $interval(poller, 1000, 0, false);
            }
        });
    };
    poller();
});
