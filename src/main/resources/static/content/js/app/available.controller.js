diamondApp.controller('AvailableController', function AvailableController($scope, $http, $rootScope, $interval, $timeout, AvailableService) {
    var self = this;
    $scope.chooseAvailableDiamond = function(diamond){
        $rootScope.$broadcast('buyDiamondChoosed', diamond);
    }

    $scope.searchInputValue = "";

    $scope.getAvailableByName = function () {
        getAvailable($scope.searchInputValue);
    }

    var getDiamondsSpreads = function () {
        return $http.post('/book-order/get-diamonds-spread', self.availableDiamonds).then(function(response) {
            //console.log(response);
            var pairs = response.data;
            for(var i in pairs){
                var pair = pairs[i];
                var diamond = pair.first;
                var askBidPair = pair.second;

                for(var j in self.availableDiamonds){
                    var currentDiamond = self.availableDiamonds[j];
                    if(currentDiamond.id == diamond.id){
                        currentDiamond.askBidPair = askBidPair;
                    }
                }
            }
        });
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
    window.setInterval(getDiamondsSpreads, 1000);
});