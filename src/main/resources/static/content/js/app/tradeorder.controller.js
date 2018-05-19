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

    var poller1 = function() {
        if(DiamondService.getCurrentDiamond()==null){
            $timeout(poller1, 150, false);
            return;
        }
        TradeOrderService.getHistoryOrders(DiamondService.getCurrentDiamond()).then(function (data) {
            self.historyTradeOrders = data;
            if(self.firstTimeOut) {
                self.firstTimeOut = false;
                window.setInterval(poller1, 1000);
            }
        });
    };

    var poller2 = function() {
        var getLiveOrdersStatus = function () {
            if(self.liveTradeOrders==null ||  self.liveTradeOrders.content==null){
                return;
            }
            var ids = new Array();
            console.log(self.liveTradeOrders.content)
            for(var i in self.liveTradeOrders.content){
                ids.push(self.liveTradeOrders.content[i].id)
            }
            $http.post("/trade-order/live-orders-reread", ids).then(function (responce) {
                self.liveTradeOrders.content = responce.data;
            });
        }
        getLiveOrdersStatus();
    };

    poller1();
    window.setInterval(poller2, 1000);

});
