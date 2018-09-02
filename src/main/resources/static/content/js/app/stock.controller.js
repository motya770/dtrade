diamondApp.controller('StockController', function (AccountService, DiamondService, $scope, $rootScope, $http) {
    var self = this;

    AccountService.currentAccountCall().then(function (account) {
        if(account.account!="empty"){
            $http.post('/stock/owned', null).then(function(response) {
                self.stocks = response.data;
            });
        }
    });

    $scope.chooseStockDiamond = function (currency) {
        var diamond = DiamondService.getDiamondByBaseCurrency();
        $rootScope.$broadcast('buyDiamondChoosed', diamond);
    }
});