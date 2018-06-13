diamondApp.controller("CoinPaymentController", function CoinPaymentController($scope, $http, AccountService, AlertService){
    var self = this;
    var poller1 = function() {
        $http.post('/coin-payment/get-by-account', null).then(function (response) {
            self.coinPayments =  response.data;
        });
    };

    window.setInterval(poller1, 1000);

    self.depositRequest = {
        amount: 200
    }

    self.withdrawRequest = {
        currencyCoin: "ETH",
        currencyFiat: "USD",
        address: "",
        amount: 10
    }

    $scope.createWithdraw = function (withdrawRequest) {

        $http({url: "/coin-payment/create-withdraw",
            method: "POST",
            params: withdrawRequest}).then(function (response) {

            AccountService.currentAccount();
            if(response.data.error){
                AlertService.showAlert(response.data.message);
                return;
            }

        }, function (response) {
            if(response.data.error){
                AlertService.showAlert(response.data.message);

            }else{
                AlertService.showAlert(response);
            }
        });
    }
});




