diamondApp.controller("CoinPaymentController", function CoinPaymentController($scope, $http){
    var self = this;
    self.coinPayments =  $http.post('/coin-payment/get-by-account', null).then(function (response) {
        return response.data;
    });
});




