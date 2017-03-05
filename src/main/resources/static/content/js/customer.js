// Define the `phonecatApp` module
var diamondApp = angular.module('diamondApp', []);

// Define the `PhoneListController` controller on the `phonecatApp` module
 diamondApp.controller('CustomerController', function CustomerController($scope, $http, $rootScope) {

     var self = this;

     $http.post('/customer/diamond-activities', null).then(function(response) {
         self.diamondActivities = response.data;
     });

     $http.post('/customer/balance-activities', null).then(function(response) {
         self.balanceActivities = response.data;
     });
 });
