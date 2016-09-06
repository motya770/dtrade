// Define the `phonecatApp` module
var diamondApp = angular.module('diamondApp', ["highcharts-ng"]);

// Define the `PhoneListController` controller on the `phonecatApp` module
diamondApp.controller('AvailableController', function AvailableController($scope, $http) {
    var self = this;
    $http.get('/diamond/available').then(function(response) {
        self.availableDiamonds = response.data;
    });
});

// Define the `PhoneListController` controller on the `phonecatApp` module
diamondApp.controller('OwnedController', function OwnedController($scope, $http) {
    var self = this;
    $http.get('/diamond/owned').then(function(response) {
        self.ownedDiamonds = response.data;
    });
});

// Define the `PhoneListController` controller on the `phonecatApp` module
diamondApp.controller('SaleController', function SaleController($scope, $http) {
    var self = this;
    $http.get('/diamond/sale').then(function(response) {
        self.saleDiamonds = response.data;
    });
});

diamondApp.controller('myctrl', function ($scope, $timeout, $http) {
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

    //pushToChart(data);

    var self = this;
    $http.get('/graph/get-quotes?diamond=1').then(function(response) {
        var data = response.data;

        var formattedData = [data.length + 1];

        for (var i in data) {
            var point = new Array(2);
            point[0] = data[i].time;
            point[1] = data[i].value;
            formattedData[i] = point;
        }

        pushToChart(formattedData);
    });
});