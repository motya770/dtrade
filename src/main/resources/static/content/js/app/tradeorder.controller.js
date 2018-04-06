diamondApp.controller("TradeOrderController", function TradeOrderController($scope, $rootScope, $http, $interval, AccountService, TradeOrderService){

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
        TradeOrderService.getHistoryOrders().then(function (data) {
            self.historyTradeOrders = data;
            if(self.firstTimeOut) {
                self.firstTimeOut = false;
                $interval(poller, 1000);
            }
        });
    };
    poller();
});
