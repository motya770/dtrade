diamondApp.controller('OwnedController', function OwnedController($scope, $http, $rootScope, MyDiamondsService) {
    var self = this;

    MyDiamondsService.getOwnedDiamonds().then(function (data) {
        self.ownedDiamonds = data;
    });

    $scope.chooseOwnedDiamond = function(diamond){
        $rootScope.$broadcast('ownedDiamondChoosed', diamond);
    }
});