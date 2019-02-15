diamondApp.controller("TradeOrderAccountController", function TradeOrderAccountController($scope, $rootScope, $http, AccountService, TradeOrderAccountService){

    var self = this;

    $scope.getPreviousAccountHistoryOrders = function (pageNumber) {
        TradeOrderAccountService.getAccountHistoryTradeOrders(pageNumber + 1).then(function (data) {
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

    TradeOrderAccountService.getAccountHistoryTradeOrders().then(function (data) {
        self.accountHistoryTradeOrders = data;
    });

});
