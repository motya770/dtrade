diamondApp.controller('BalanceController', function BalanceController(AvailableService, $rootScope, $scope, $http) {
    var self = this;
    $http.post('/balance/get-balances', null).then(function(response) {
        self.balances = response.data;
    });
    $scope.chooseBalance = function (currency) {
        var diamond = AvailableService.getDiamondByBaseCurrency(currency);
        $rootScope.$broadcast('buyDiamondChoosed', diamond);
    }
});