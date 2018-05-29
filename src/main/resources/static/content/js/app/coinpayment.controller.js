diamondApp.controller("CoinPaymentController", function CoinPaymentController($scope, $http){
    var self = this;
    $http.post('/coin-payment/get-by-account', null).then(function (response) {
        self.coinPayments =  response.data;
    });

    self.withdrawRequest = {
        currencyCoin: "ETH",
        currencyFiat: "USD",
        address: "0x10D75F90b0F483942aDd5a947b71D8617BB012eD",
        amount: 5
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




