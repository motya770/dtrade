diamondApp.controller('AvailableController', function AvailableController($scope, $http, $rootScope, AvailableService) {
    var self = this;
    $scope.chooseAvailableDiamond = function(diamond){
        $rootScope.$broadcast('buyDiamondChoosed', diamond);
    }

    $scope.searchInputValue = "";

    $scope.getAvailableByName = function () {
        getAvailable($scope.searchInputValue);
    }

    var getAvailable = function (name) {
        AvailableService.getAvailable(name).then(function (data) {
            //TODO only first 5 
            var onlyFive = [];
            for(var i in data){
                onlyFive[i] = data[i];
                if(i > 3){
                    break;
                }
            }

            self.availableDiamonds = onlyFive;
            if(data!=null && data.length>0){
                var firstDiamond = data[0];
                $rootScope.$broadcast('buyDiamondChoosed', firstDiamond);
            }
        });
    }

    getAvailable("");//all

});