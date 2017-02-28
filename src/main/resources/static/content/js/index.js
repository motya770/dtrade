// Define the `phonecatApp` module
var diamondApp = angular.module('diamondApp', ["highcharts-ng"]);

// Define the `PhoneListController` controller on the `phonecatApp` module
diamondApp.controller('AvailableController', function AvailableController($scope, $http, $rootScope) {
    var self = this;
    $scope.chooseAvailableDiamond = function(diamond){
        $rootScope.$broadcast('buyDiamondChoosed', diamond);
    }

    $http.get('/diamond/available').then(function(response) {
        self.availableDiamonds = response.data;
    });
});

// Define the `PhoneListController` controller on the `phonecatApp` module
diamondApp.controller('OwnedController', function OwnedController($scope, $http, $rootScope, MyDiamondsService) {
    var self = this;

    MyDiamondsService.getOwnedDiamonds().then(function (data) {
        self.ownedDiamonds = data;
    });

    // $http.get('/diamond/my-owned').then(function(response) {
    //     self.ownedDiamonds = response.data;
    // });

    $scope.chooseOwnedDiamond = function(diamond){
        $rootScope.$broadcast('sellDiamondChoosed', diamond);
    }
});

// Define the `PhoneListController` controller on the `phonecatApp` module
diamondApp.controller('SaleController', function SaleController($scope, $http, MyDiamondsService) {
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
});

function arrayObjectIndexOf(arr, obj){
    for(var i = 0; i < arr.length; i++){

        if(arr[i].id == obj.id){
            return i;
        }

        // if(angular.equals(arr[i], obj)){
        //     return i;
        // }
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
             return $http.get('/diamond/my-owned').then(function(response) {
                 ownedDiamonds = response.data;
                 return ownedDiamonds;
             });
         }
    };

    var getForSaleDiamonds = function() {
        if (saleDiamonds != null && saleDiamonds.length != 0) {
            return $q.resolve(saleDiamonds)
        }else{
            return $http.get('/diamond/my-for-sale').then(function (response) {
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


diamondApp.controller("BidderController", function BidderController($scope, $rootScope, $http, AccountService, MyDiamondsService){
    var self= this;

    AccountService.currentAccount().then(function (currentAccount) {
        self.currentAccount = currentAccount;
    });

    $scope.buyDiamond = function(diamond, currentAccount){

            $http.post("/diamond/buy?buyerId=" + currentAccount.id
                + "&price=" + diamond.price, diamond).then(function(responce){

                MyDiamondsService.addOwned(responce.data);

                //console.log("buy: " + responce);
                // responce.data;
               // $scope.saleDiamonds.push(responce.data);
            });
    };

    $scope.sellDiamond = function(diamond, currentAccount){

        $http.post("/diamond/open-for-sale?byuerId=" + currentAccount.id + "&price=" + diamond.price, diamond).then(function(responce){

            console.log("sell: " + responce);
            MyDiamondsService.addForSale(responce.data);

        });
    };

    $scope.$on('buyDiamondChoosed', function (event, arg) {
        self.buyDiamond = arg;
    });

    $scope.$on('sellDiamondChoosed', function (event, arg) {
        self.sellDiamond = arg;
    });
});


diamondApp.controller('ChartController', function ($scope, $timeout, $http) {
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

    var pushToChart = function(data){
        $scope.chartConfig.series.push({
                id: 1,
                data: data
            }
        );
    };

    var self = this;
    var diamondId = 1;
    var getChartData = function(diamondId){
        $http.get('/graph/get-quotes?diamond=' + diamondId).then(function(response) {
            var data = response.data;

            console.log("l:" + data.length)
            var formattedData = [data.length + 1];

            for (var i in data) {
                var point = new Array(2);
                point[0] = data[i].time;
                point[1] = data[i].price;
                formattedData[i] = point;
            }

            pushToChart(formattedData);
        });
    };

    getChartData(diamondId);

    $scope.$on('buyDiamondChoosed', function (event, arg) {
        getChartData(arg.id);
    });

    $scope.$on('sellDiamondChoosed', function (event, arg) {
        getChartData(arg.id);
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

    // return {
    //
    //     currentAccount: function() {
    //
    //         if(currentAccount==null){
    //             $http.post('/accounts/get-current').then(function(response){
    //                 self.user = response.data;
    //             });
    //         }
    //
    //         return currentAccount;
    //     }
    //
    // };
}]);


// diamondApp.factory('AccountService', function($http) {
//
//      return {
//          user: self.user
//      }
// });

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
    // $http.post('/accounts/get-current').then(function(response){
    //     self.user = response.data;
    //     //$scope["currentAccount"] = self.user;
    // });
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

