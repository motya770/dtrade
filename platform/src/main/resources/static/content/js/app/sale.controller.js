diamondApp.controller('SaleController', function SaleController($scope, $http, $rootScope, SaleService) {
    var self = this;

    SaleService.getForSaleDiamonds().then(function (data) {
        self.saleDiamonds = data;
    });

    $scope.hideFromSale = function (diamond) {
        $http.post("/diamond/hide-from-sale", diamond).then(function (responce) {
            var diamond = responce.data;
            SaleService.hideFromSale(diamond);
        });
    }

    $scope.chooseOpenForSale = function(diamond){
        $rootScope.$broadcast('openForSaleDiamondChoosed', diamond);
    }
});