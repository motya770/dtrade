diamondApp.controller('AvailableController', function AvailableController($scope, $http, $rootScope, AvailableService) {
    var self = this;
    $scope.chooseAvailableDiamond = function(diamond){
        $rootScope.$broadcast('buyDiamondChoosed', diamond);
    }

    AvailableService.getAvailable().then(function (data) {
        self.availableDiamonds = data;
    })
});