var diamondApp = angular.module('diamondApp', ['ngMaterial','ngMessages'])

.controller("TopController", [ '$scope', '$timeout', '$mdDialog', function($scope, $timeout, $mdDialog, AccountSesrvice) {
    $scope.loaded = false;
   // $scope.title = "This is an App";
    $scope.$on('accountReceived', function (event, arg) {
        if(!$scope.loaded){
            $scope.loaded = true;
        }
    });


$scope.showAdvanced = function(ev) {
    $mdDialog.show({
      controller: DialogController,
      templateUrl: 'dialog1.tmpl.html',
      parent: angular.element(document.body),
      targetEvent: ev,
      clickOutsideToClose:true
    })
  };

  function DialogController($scope, $mdDialog) {
    $scope.hide = function() {
      $mdDialog.hide();
    };

    $scope.cancel = function() {
      $mdDialog.cancel();
    };

    $scope.answer = function(answer) {
      $mdDialog.hide(answer);
    };
  }
   //$timeout(function() { $scope.loaded = true; }, 100000);
}]);




