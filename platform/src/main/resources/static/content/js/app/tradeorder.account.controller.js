diamondApp.controller("TradeOrderAccountController", function TradeOrderAccountController($scope, $rootScope, $http, AccountService, TradeOrderAccountService){

    var self = this;

    self.fromTime = moment().startOf('month')._d;
    self.toTime = new Date();

    $scope.getPreviousAccountHistoryOrders = function (pageNumber) {
        TradeOrderAccountService.getAccountHistoryTradeOrders(self.fromTime.getTime(), self.toTime.getTime(), pageNumber + 1).then(function (data) {
            self.accountHistoryTradeOrders = data;
        });
    }

    $scope.getTotalProfit = function(){
        var totalProfit = 0;
        if(self.accountHistoryTradeOrders!=undefined && self.accountHistoryTradeOrders.content!=undefined) {
            for (var i = 0; i < self.accountHistoryTradeOrders.content.length; i++) {
                totalProfit += self.accountHistoryTradeOrders.content[i].profit;
            }
        }
        return totalProfit;
    }

    $scope.filter = function(){
        TradeOrderAccountService.getAccountHistoryTradeOrders(self.fromTime.getTime(), self.toTime.getTime()).then(function (data) {
            self.accountHistoryTradeOrders = data;
        });
    }

    TradeOrderAccountService.getAccountHistoryTradeOrders(self.fromTime.getTime(), self.toTime.getTime()).then(function (data) {
        self.accountHistoryTradeOrders = data;
    });

});
