// Define the `phonecatApp` module
var diamondApp = angular.module('diamondApp', ['ngMaterial']);

/*
angular.module('diamondApp')
.run(function($timeout, $rootScope) {

    $timeout(function() { // simulate long page loading
        $rootScope.isLoading = false; // turn "off" the flag
    }, 3000)

});*/


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

/*
angular
 .module('diamondApp', ['ngMaterial'], function($mdThemingProvider) {
    $mdThemingProvider.theme('docs-dark', 'default')
      .primaryPalette('yellow')
      .dark();
  })
  .controller('AppCtrl', ['$interval',
    function($interval) {
      var self = this;

     self.activated = true;
      self.determinateValue = 30;

       Iterate every 100ms, non-stop and increment
       the Determinate loader.
      $interval(function() {

        self.determinateValue += 1;
        if (self.determinateValue > 100) {
          self.determinateValue = 30;
        }

      }, 100);
    }
  ]);*/

/*
diamondApp.controller('LoginController', ['$scope', '$http', function($scope, $http) {
    $scope.master = {};

    $scope.login = function(user) {

        $http.get('/accounts/get-current', user).then(function(response){

            console.log(response);

        });
    };
}]);*/


