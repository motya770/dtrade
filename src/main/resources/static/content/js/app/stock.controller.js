diamondApp.controller('StockController', function ($scope, $rootScope, $http) {
    var self = this;
    $http.post('/stock/owned', null).then(function(response) {
        self.stocks = response.data;
    });

    $scope.chooseStockDiamond = function (diamond) {
        $rootScope.$broadcast('buyDiamondChoosed', diamond);
    }
});