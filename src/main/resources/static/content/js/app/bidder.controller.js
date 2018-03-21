diamondApp.controller("BidderController", function BidderController($scope, $rootScope, $http, AccountService, TradeOrderService){
    var self= this;
    self.buyOrder = {};
    self.sellOrder = {};

    AccountService.currentAccount().then(function (currentAccount) {
        self.currentAccount = currentAccount;
    });

    $scope.tradeOrder = {
        diamond : '',
        initialAmount : '',
        price: null,
        account : null,
        tradeOrderType: null
    };

    //TODO check it!
    $scope.createTradeOrder = function (tradeOrder, diamond) {

        tradeOrder["account"] = self.currentAccount;
        tradeOrder["diamond"] = diamond;
        tradeOrder.tradeOrderType = $scope.tradeOrder.tradeOrderType;

        $http.post("/trade-order/create", tradeOrder, null).then(function (response) {
            TradeOrderService.addLiveOrder(response.data);
        })
    };

    $scope.sellOption = function (tradeOrder) {
        $scope.tradeOrder.tradeOrderType = "SELL";
    };

    $scope.buyOption = function (tradeOrder) {
        $scope.tradeOrder.tradeOrderType = "BUY";
    };

    $scope.$on('buyDiamondChoosed', function (event, arg) {
        self.diamond = arg;
    });

    $scope.$on('buyDiamondChoosed', function (event, arg) {
        self.diamond = arg;
    });
});