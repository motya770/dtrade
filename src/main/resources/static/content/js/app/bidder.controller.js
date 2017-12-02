diamondApp.controller("BidderController", function BidderController($scope, $rootScope, $http, AccountService, TradeOrderService){
    var self= this;
    self.buyOrder = {};
    self.sellOrder = {};

    AccountService.currentAccount().then(function (currentAccount) {
        self.currentAccount = currentAccount;
    });

    $scope.createTradeOrder = function (tradeOrder, diamond) {

        tradeOrder["account"] = self.currentAccount;
        tradeOrder["diamond"] = diamond;

        $http.post("/trade-order/create", tradeOrder, null).then(function (response) {
            TradeOrderService.addLiveOrder(response.data);
        })
    };

    $scope.$on('buyDiamondChoosed', function (event, arg) {
        self.buyDiamond = arg;
    });

    $scope.$on('buyDiamondChoosed', function (event, arg) {
        self.sellDiamond = arg;
    });
});