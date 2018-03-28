diamondApp.controller("TradeOrderController", function TradeOrderController($scope, $rootScope, $http, AccountService, TradeOrderService){

    var self = this;

    $scope.cancelTradeOrder = function(tradeOrder) {
        $http.post("/trade-order/cancel", tradeOrder, null).then(function (response) {
            console.log("tradeOrder canceled  " + response.data);
            TradeOrderService.addHistoryOrder(response.data);
        });
    }

    TradeOrderService.getLiveOrders().then(function (data) {
        self.liveTradeOrders = data;
    });

    TradeOrderService.getHistoryOrders().then(function (data) {
        self.historyTradeOrders = data;
    })
});
