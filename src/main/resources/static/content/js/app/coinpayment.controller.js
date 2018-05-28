diamondApp.controller("CoinPaymentController", function CoinPaymentController($scope, $http){
    var self = this;
    $http.post('/coin-payment/get-by-account', null).then(function (response) {
        self.coinPayments =  response.data;
    });

    self.withdrawRequest = {
        currencyCoin: null,
        currencyFiat: 5,
        address: null,
        note: null
    }

    $scope.createWithdraw = function (withdrawRequest) {

        $http({url: "/coin-payment/create-withdraw",
            method: "POST",
            params: withdrawRequest}).then(function (response) {
            console.log(response);
        });

        /*
        $http.post('/coin-payment/create-withdraw', withdrawRequest).then(function (response) {
            console.log(response);
        });*/
    }
});




