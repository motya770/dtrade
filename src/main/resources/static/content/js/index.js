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
diamondApp.controller('OwnedController', function OwnedController($scope, $http, $rootScope) {
    var self = this;
    $http.get('/diamond/owned').then(function(response) {
        self.ownedDiamonds = response.data;
    });

    $scope.chooseOwnedDiamond = function(diamond){
        $rootScope.$broadcast('sellDiamondChoosed', diamond);
    }
});

// Define the `PhoneListController` controller on the `phonecatApp` module
diamondApp.controller('SaleController', function SaleController($scope, $http) {
    var self = this;
    $http.get('/diamond/sale').then(function(response) {
        self.saleDiamonds = response.data;
    });
});

diamondApp.controller("BidderController", function BidderController($scope, $rootScope){
    var self= this;

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
                point[1] = data[i].value;
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