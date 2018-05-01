// Define the `phonecatApp` module
var diamondApp = angular.module('diamondApp', ['ngMaterial']);


diamondApp.controller("TopController", [ '$scope', '$timeout', function($scope, $timeout, AccountSesrvice) {
    $scope.loaded = false;
   // $scope.title = "This is an App";
    $scope.$on('accountReceived', function (event, arg) {
        if(!$scope.loaded){
            $scope.loaded = true;
        }
    });
   //$timeout(function() { $scope.loaded = true; }, 100000);
}]);




