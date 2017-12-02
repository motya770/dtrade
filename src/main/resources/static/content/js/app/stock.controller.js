diamondApp.controller('StockController', function ($http) {
    var self = this;
    $http.post('/stock/owned', null).then(function(response) {
        self.stocks = response.data;
    });
});