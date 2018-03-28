diamondApp.controller("TradeOrderAccountController", function BidderController($scope, $rootScope, $http, AccountService, TradeOrderAccountService){

    var self = this;

    TradeOrderAccountService.getAccountHistoryTradeOrders().then(function (data) {
        self.accountHistoryTradeOrders = data;
    })

});
