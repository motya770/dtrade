diamondApp.controller("CoinPaymentController", function CoinPaymentController($scope, $http){
    var self = this;
    $http.post('/coin-payment/get-by-account', null).then(function (response) {
        self.coinPayments =  response.data;
    });
});




