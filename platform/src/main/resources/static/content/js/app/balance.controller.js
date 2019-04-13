diamondApp.controller('BalanceController', function BalanceController(AvailableService, AccountService, $rootScope, $scope, $http) {
    var self = this;


    $scope.$watch(AccountService.currentAccount, function (value, oldValue) {

        if(!value){
            return;
        }

        if(value.account==="empty"){
            return;
        }

        $http.post('/balance/get-balances', null).then(function(response) {
            self.balances = response.data;
        });
        $http.post('/balance/get-deposits-withdrawals', null).then(function(response) {
            self.depositsWithdrawals = response.data;
        });
    }, true);

    $scope.chooseBalance = function (currency) {
        var diamond = AvailableService.getDiamondByBaseCurrency(currency);
        $rootScope.$broadcast('buyDiamondChoosed', diamond);
    }
});