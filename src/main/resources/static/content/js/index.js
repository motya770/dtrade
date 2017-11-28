// Define the `phonecatApp` module
var diamondApp = angular.module('diamondApp', ["highcharts-ng"]);

// Define the `PhoneListController` controller on the `phonecatApp` module
diamondApp.controller('AvailableController', function AvailableController($scope, $http, $rootScope, AvailableService) {
    var self = this;
    $scope.chooseAvailableDiamond = function(diamond){
        $rootScope.$broadcast('buyDiamondChoosed', diamond);
    }

    AvailableService.getAvailable().then(function (data) {
        self.availableDiamonds = data;
    })
});

diamondApp.service('AvailableService', function($http, $q){
    var availableDiamonds = [];

    var getAvailable = function() {
        if(availableDiamonds!=null && availableDiamonds.length > 0) {
            return $q.resolve(ownedDiamonds)
        }else{
            return $http.post('/diamond/available', null).then(function(response) {
                availableDiamonds = response.data;
                return availableDiamonds;
            });
        }
    };

    return {
        getAvailable: getAvailable
    };
});


diamondApp.service('BookOrderService', function BookOrderService($http){
    var getBookOrder = function(diamond) {
        return $http.post('/book-order/', diamond, null).then(function (response) {
                return response.data;
        });
    };
    return {
        getBookOrder: getBookOrder
    };
});


diamondApp.controller("BookOrderController", function BookOrderController($scope, $http, $rootScope, BookOrderService) {

    var self = this;

    function callBookOrderService() {
        BookOrderService.getBookOrder(self.diamond).then(function (data) {
            self.bookOrder = data;
        });
    }

    $scope.$on('buyDiamondChoosed', function (event, arg) {
        self.diamond = arg;
        callBookOrderService();
    });

    $scope.$on('ownedDiamondChoosed', function (event, arg) {
        self.diamond = arg;
        callBookOrderService();
    });

    $scope.$on('openForSaleDiamondChoosed', function (event, arg) {
        self.diamond = arg;
        callBookOrderService();
    });

    $scope.$on('buyDiamondChoosed', function (event, arg) {
        self.diamond = arg;
        callBookOrderService();
    });
});

// Define the `PhoneListController` controller on the `phonecatApp` module
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


diamondApp.controller('StockController', function ($http) {

    var self = this;


    $http.post('/stock/owned', null).then(function(response) {
            self.stocks = response.data;
    });

});

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

diamondApp.controller("BidderController", function BidderController($scope, $rootScope, $http, AccountService, TradeOrderService){
    var self= this;
    self.buyOrder = {};
    self.sellOrder = {};

    AccountService.currentAccount().then(function (currentAccount) {
        self.currentAccount = currentAccount;
    });

    $scope.createTradeOrder = function (tradeOrder, diamond) {

        tradeOrder["account"] = self.currentAccount;
        tradeOrder["diamond"] = diamond;

        $http.post("/trade-order/create", tradeOrder, null).then(function (response) {
            TradeOrderService.addLiveOrder(response.data);
        })
    };

    $scope.$on('buyDiamondChoosed', function (event, arg) {
        self.buyDiamond = arg;
    });

    $scope.$on('buyDiamondChoosed', function (event, arg) {
        self.sellDiamond = arg;
    });
});

diamondApp.controller('ChartController', function ($scope, $timeout, $http, AvailableService) {
    $scope.chartConfig = {
        options: {
            chart: {
                zoomType: 'x'
            },
            rangeSelector: {
                enabled: true
            },
            navigator: {
                enabled: true
            }
        },

        series: [],
        title: {
            text: 'Diamond Price'
        },

        useHighStocks: true
    }

    var pushToChart = function(data, seriesId, name){
        $scope.chartConfig.series.push({
                id: seriesId,
                data: data,
                name: name
            }
        );
    };

    var self = this;
    //var diamondId = 1;
    var getChartData = function(diamondId){
        $http.post('/graph/get-quotes?diamond=' + diamondId, null).then(function(response) {
            var data = response.data;

            console.log("l:" + data.length)
            var bids = [data.length + 1];
            var asks = [data.length + 1];
            for (var i in data) {
                var bidPoint = new Array(2);
                bidPoint[0] = data[i].time;
                bidPoint[1] = data[i].bid;
                bids[i] = bidPoint;

                var askPoint = new Array(2);
                askPoint[0] = data[i].time;
                askPoint[1] = data[i].ask;
                asks[i] = askPoint;
            }

            pushToChart(bids, 1, "Bids");
            pushToChart(asks, 2, "Asks");
        });
    };


    var getCategoryScoreData = function (score) {

        //TODO
        return;
        $http.post("/category-tick/for-score?score="+score, null).then(function (responce) {
            var data = responce.data;
            var formattedData = [data.length + 1];
            for (var i in data){
                var point = new Array(2);
                point[0] = data[i].time;
                point[1] = data[i].avarage;
                formattedData[i] = point;
            }

            pushToChart(formattedData, 2);

        });
    }


    //init for graph
    AvailableService.getAvailable().then(function (data) {
        if(data!=null && data.length>0){
            var firstDiamond = data[0];
             getChartData(firstDiamond.id);
             getCategoryScoreData(firstDiamond.score);
        }
    })


    $scope.$on('buyDiamondChoosed', function (event, arg) {
        getChartData(arg.id);
        getCategoryScoreData(arg.score);
    });

    $scope.$on('ownedDiamondChoosed', function (event, arg) {
        getChartData(arg.id);
        getCategoryScoreData(arg.score);
    });

    $scope.$on('openForSaleDiamondChoosed', function (event, arg) {
        getChartData(arg.id);
        getCategoryScoreData(arg.score);
    });
});

diamondApp.controller('RegisterController', ['$scope', '$http', function($scope, $http) {
        $scope.master = {};

        $scope.update = function(user) {
            //$scope.master = angular.copy(user);

            /*var config = {
                headers : {
                    'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
                }
            };*/

            $http.post('/accounts/register', user).then(function(response){
               debugger;
                //$scope.status = response.status;
                //$scope.data = response.data;
            });
        };
}]);


diamondApp.factory( 'AccountService', ["$http", function($http) {

    var self = this;
    var currentAccount;

    if(currentAccount!=null) {
        return {
            currentAccount: function () {
                return  currentAccount;
            }
        }
    }else{
        return{
            currentAccount: function () {
                return $http.post('/accounts/get-current').then(function(response){
                    currentAccount = response.data;
                    return currentAccount;
                });
            }
        }
    }
}]);

// app.controller( 'MainCtrl', function( $scope, AuthService ) {
//     $scope.$watch( AuthService.isLoggedIn, function ( isLoggedIn ) {
//         $scope.isLoggedIn = isLoggedIn;
//         $scope.currentUser = AuthService.currentUser();
//     });
// });

diamondApp.controller('AccountController', ['$scope', "$http", "AccountService", function($scope, $http, AccountService) {
    var self = this;
    AccountService.currentAccount().then(function (user) {
        self.user = user;
    });
}]);


/*
diamondApp.controller('LoginController', ['$scope', '$http', function($scope, $http) {
    $scope.master = {};

    $scope.login = function(user) {

        $http.get('/accounts/get-current', user).then(function(response){

            console.log(response);

        });
    };
}]);*/

