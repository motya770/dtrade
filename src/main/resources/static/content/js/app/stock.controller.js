diamondApp.controller('StockController', function (AccountService, $scope, $rootScope, $http) {
    var self = this;

    AccountService.currentAccount().then(function (account) {
        if(account.account!="empty"){
            $http.post('/stock/owned', null).then(function(response) {
                self.stocks = response.data;
            });
        }
    });

    $scope.chooseStockDiamond = function (diamond) {
        $rootScope.$broadcast('buyDiamondChoosed', diamond);
    }
});