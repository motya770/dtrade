// Define the `phonecatApp` module
var diamondApp = angular.module('diamondApp', ["highcharts-ng"]);

diamondApp.controller('OwnedController', function OwnedController($scope, $http, $rootScope, MyDiamondsService) {
    var self = this;

    MyDiamondsService.getOwnedDiamonds().then(function (data) {
        self.ownedDiamonds = data;
    });

    $scope.chooseOwnedDiamond = function(diamond){
        $rootScope.$broadcast('ownedDiamondChoosed', diamond);
    }
});

diamondApp.controller('SaleController', function SaleController($scope, $http, $rootScope, MyDiamondsService) {
        var self = this;

        MyDiamondsService.getForSaleDiamonds().then(function (data) {
            self.saleDiamonds = data;
        });

        $scope.hideFromSale = function (diamond) {
            $http.post("/diamond/hide-from-sale", diamond).then(function (responce) {
                var diamond = responce.data;
                MyDiamondsService.hideFromSale(diamond);
            });
        }

    $scope.chooseOpenForSale = function(diamond){
        $rootScope.$broadcast('openForSaleDiamondChoosed', diamond);
    }
});

function arrayObjectIndexOf(arr, obj){
    for(var i = 0; i < arr.length; i++){

        if(arr[i].id == obj.id){
            return i;
        }
    };
    return -1;
}

diamondApp.service('MyDiamondsService', function($http, $q){
    var ownedDiamonds = [];
    var saleDiamonds = [];

    var hideFromSale = function(diamond) {
        saleDiamonds.splice(arrayObjectIndexOf(saleDiamonds, diamond),1);
        ownedDiamonds.push(diamond);
    };

    var addForSale = function (diamond) {
        saleDiamonds.push(diamond);
        ownedDiamonds.splice(arrayObjectIndexOf(ownedDiamonds, diamond),1);
    };

    var addOwned = function (diamond) {
        ownedDiamonds.push(diamond);
    };

    var getOwnedDiamonds = function() {
          if (ownedDiamonds != null && ownedDiamonds.length != 0) {
              return $q.resolve(ownedDiamonds)
          }else{
             return $http.post('/diamond/my-owned', null).then(function(response) {
                 ownedDiamonds = response.data;
                 return ownedDiamonds;
             });
         }
    };

    var getForSaleDiamonds = function() {
        if (saleDiamonds != null && saleDiamonds.length != 0) {
            return $q.resolve(saleDiamonds)
        }else{
            return $http.post('/diamond/my-for-sale', null).then(function (response) {
                saleDiamonds = response.data;
                return saleDiamonds;
            });
        }
    };

    return {
        hideFromSale: hideFromSale,
        getOwnedDiamonds: getOwnedDiamonds,
        getForSaleDiamonds: getForSaleDiamonds,
        addForSale: addForSale,
        addOwned: addOwned
    };
});

diamondApp.service("TradeOrderService", function ($http, $q) {

    var liveOrders = [];
    var historyOrders = [];

    var addLiveOrder = function(order){
        liveOrders.push(order);
    };


    var addHistoryOrder = function (order) {
        liveOrders.splice(arrayObjectIndexOf(liveOrders, order),1);
        historyOrders.push(order);
    };

    var getHistoryOrders = function () {

        if (historyOrders != null && historyOrders.length != 0) {
            return $q.resolve(historyOrders)
        }else{
            return $http.post("/trade-order/history-orders", null, null).then(function (responce) {
                historyOrders = responce.data;
                return historyOrders;
            });
        }
    };

    var getLiveOrders = function () {

        if (liveOrders != null && liveOrders.length != 0) {
            return $q.resolve(liveOrders)
        }else{
            return $http.post("/trade-order/live-orders", null, null).then(function (response) {
                liveOrders = response.data;
                return liveOrders;
            });
        }
    };

    return {
        getHistoryOrders: getHistoryOrders,
        getLiveOrders: getLiveOrders,
        addLiveOrder: addLiveOrder,
        addHistoryOrder: addHistoryOrder
    }
});

diamondApp.controller("TradeOrderController", function BidderController($scope, $rootScope, $http, AccountService, TradeOrderService){

    var self = this;

    $scope.cancelTradeOrder = function(tradeOrder) {
        $http.post("/trade-order/cancel", tradeOrder, null).then(function (response) {
            console.log("tradeOrder canceled  " + response.data);
            TradeOrderService.addHistoryOrder(response.data);
        });
    }

    TradeOrderService.getLiveOrders().then(function (data) {
        self.liveTradeOrders = data;
    });

    TradeOrderService.getHistoryOrders().then(function (data) {
         self.historyTradeOrders = data;
    })
});

/*
diamondApp.controller('LoginController', ['$scope', '$http', function($scope, $http) {
    $scope.master = {};

    $scope.login = function(user) {

        $http.get('/accounts/get-current', user).then(function(response){

            console.log(response);

        });
    };
}]);*/

