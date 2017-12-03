diamondApp.controller('OwnedController', function OwnedController($scope, $http, $rootScope, OwnedService) {
    var self = this;

    OwnedService.getOwnedDiamonds().then(function (data) {
        self.ownedDiamonds = data;
    });

    $scope.chooseOwnedDiamond = function(diamond){
        $rootScope.$broadcast('ownedDiamondChoosed', diamond);
    }
});