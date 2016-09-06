// Define the `phonecatApp` module
var diamondApp = angular.module('diamondApp', []);

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
