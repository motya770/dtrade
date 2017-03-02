// Define the `phonecatApp` module
var diamondApp = angular.module('diamondApp', []);

// Define the `PhoneListController` controller on the `phonecatApp` module
 diamondApp.controller('CustomerController', function CustomerController($scope, $http, $rootScope) {

     var self = this;

     $http.get('/customer/diamond-activities').then(function(response) {
         self.diamondActivities = response.data;
     });

     $http.get('/customer/balance-activities').then(function(response) {
         self.balanceActivities = response.data;
     });
 });
