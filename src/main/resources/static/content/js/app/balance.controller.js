diamondApp.controller('BalanceController', function BalanceController($scope, $http) {
    var self = this;
    $http.post('/balance/get-balances', null).then(function(response) {
        self.balances = response.data;
    });
});