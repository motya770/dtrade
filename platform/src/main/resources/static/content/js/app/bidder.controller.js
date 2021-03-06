diamondApp.controller("BidderController", function BidderController($scope, $rootScope, $http,
                                                                    AccountService, TradeOrderService, AlertService){
    var self= this;
    self.buyOrder = {};
    self.sellOrder = {};
    $scope.tab = 5;

    AccountService.currentAccountCall().then(function (currentAccount) {
        self.currentAccount = currentAccount;
    });

    self.tradeOrder = {
        diamond : '',
        initialAmount : '',
        price: null,
        account : null,
        tradeOrderDirection: "BUY"
    };

    //TODO check it!
    $scope.createTradeOrder = function (tradeOrder, diamond, tradeOrderType) {

        if(self.currentAccount == null || self.currentAccount.account == "empty"){
            AlertService.showAlert("You should make login first!", "Notification", "EMPTY_ACCOUNT");
            return;
        }

        tradeOrder["account"] = self.currentAccount;
        tradeOrder["diamond"] = diamond;
        tradeOrder.tradeOrderDirection = self.tradeOrder.tradeOrderDirection;
        tradeOrder.tradeOrderType = tradeOrderType;

        $http.post("/trade-order/create", tradeOrder, null).then(function (response) {
            //TODO fix
            if(response.data.error){
                AlertService.showAlert(response.data.message);
                return;
            }
            if(response.data.tradeOrder.id == null) {
                AlertService.showAlert("You should make login first.", "Notification");
                return;
            }
            TradeOrderService.addLiveOrder(response.data.tradeOrder);
            AccountService.refreshAccount(response.data.accountDTO);

            //if(response.data.tradeOrderDirection=="BUY") {
               // AccountService.refreshCurrentAccount();
            //}

            //TODO add stock service
        }, function (response) {
            if(response.data.error){
                AlertService.showAlert(response.data.message);
                return;
            }
        });
    };


    $scope.sellOption = function (tradeOrder) {
        self.tradeOrder.tradeOrderDirection = "SELL";
    };

    $scope.buyOption = function (tradeOrder) {
        self.tradeOrder.tradeOrderDirection = "BUY";
    };

    $scope.$on('buyDiamondChoosed', function (event, arg) {
        self.diamond = arg;
    });
/*
    $scope.$on('buyDiamondChoosed', function (event, arg) {
        self.diamond = arg;
    });*/
});