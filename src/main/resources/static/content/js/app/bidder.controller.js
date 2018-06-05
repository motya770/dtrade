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
        tradeOrderType: "BUY"
    };

    //TODO check it!
    $scope.createTradeOrder = function (tradeOrder, diamond) {

        if(self.currentAccount == null){
            alert("You should make login first!");
        }

        tradeOrder["account"] = self.currentAccount;
        tradeOrder["diamond"] = diamond;
        tradeOrder.tradeOrderType = $scope.tradeOrder.tradeOrderType;

        $http.post("/trade-order/create", tradeOrder, null).then(function (response) {
            //TODO fix
            if(response.data.error){
                alert(response.data.message);
                return;
            }
            if(response.data.id == null) {
                alert("You should make login first.");
                return;
            }
            TradeOrderService.addLiveOrder(response.data);
        }, function (response) {
            if(response.data.error){
                alert(response.data.message);
                return;
            }
        });
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