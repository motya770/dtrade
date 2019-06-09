diamondApp.controller('AvailableController', function AvailableController($scope, $http, $rootScope, $interval, $timeout, AvailableService) {
    var self = this;
    $scope.chooseAvailableDiamond = function(diamond){
        $rootScope.$broadcast('buyDiamondChoosed', diamond);
    }

    $scope.searchInputValue = "";

    $scope.getAvailableByName = function () {
        getAvailable($scope.searchInputValue);
    }

    $scope.config = {
        autoHideScrollbar: false,
        theme: 'dark',
        advanced:{
            updateOnContentResize: true
        },
        scrollInertia: 0
    }

    var getDiamondsSpreads = function () {

        return $http.post('/book-order/get-diamonds-spread', self.availableDiamondsIds).then(function(response) {
            //console.log(response);
            var simpleQuotes = response.data;
            for(var i in simpleQuotes){
                var simpleQuote = simpleQuotes[i];
                if(simpleQuote==null){
                    continue;
                }
                var diamond = simpleQuote.diamond;
                var bid  = simpleQuote.bid;
                var ask = simpleQuote.ask;

                var avg = (bid + ask)/2;
                //if(angular.isNumber(avg)){
                    simpleQuote.avg = avg;
                //}

                for(var j in self.availableDiamonds){
                    var currentDiamond = self.availableDiamonds[j];
                    if(currentDiamond.id == diamond.id){
                        currentDiamond.simpleQuote = simpleQuote;
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
            }

            self.availableDiamonds = onlyFive;
            self.availableDiamondsIds = new Array();
            for(var i in  self.availableDiamonds ){
                self.availableDiamondsIds.push(self.availableDiamonds[i].id);
            }

            if(data!=null && data.length>0){
                var firstDiamond = data[0];
                $rootScope.$broadcast('buyDiamondChoosed', firstDiamond);
            }
        });
    }

    var promise = $interval(getDiamondsSpreads, 1000);

    $scope.$on('$destroy', function(){
        if (angular.isDefined(promise)) {
            $interval.cancel(promise);
            promise = undefined;
        }
    });

    getAvailable("");//all
    //window.setInterval(getDiamondsSpreads, 1000);
});