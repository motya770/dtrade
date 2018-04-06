diamondApp.controller("TradeOrderAccountController", function BidderController($scope, $rootScope, $http, AccountService, TradeOrderAccountService){

    var self = this;

    $scope.getPreviousAccountHistoryOrders = function (pageNumber) {
        TradeOrderAccountService.getAccountHistoryTradeOrders(pageNumber + 1).then(function (data) {
            self.accountHistoryTradeOrders = data;
        })
    }

    TradeOrderAccountService.getAccountHistoryTradeOrders().then(function (data) {
        self.accountHistoryTradeOrders = data;
    })

});
