var diamondApp = angular.module('diamondApp', ['ngMaterial','ngMessages', 'ngCookies', 'ngRoute',
    'angular-nicescroll', 'ngMaterialDatePicker'])
    .filter('diamondTypeFilter', function() {
        return function(input) {
            input = input || '';
            var out = '';
            for (var i = 0; i < 3; i++) {
                var nextChar;
                if(i==0){
                    nextChar = input.charAt(i).toUpperCase();
                }else{
                    nextChar =input.charAt(i).toLowerCase();
                }
                out = out + nextChar;
            }
            return out;
        };
    })
.controller("TopController", [ '$scope', '$timeout', '$mdDialog', function($scope, $timeout, $mdDialog, AccountSesrvice, DiamondService) {
    $scope.loaded = false;
   // $scope.title = "This is an App";
    $scope.$on('accountReceived', function (event, arg) {
        if(!$scope.loaded){
            $scope.loaded = true;
        }
    });

  $scope.showAdvanced = function(diamond, ev) {
    var self = this;
    self.currentDiamond = diamond;

    $mdDialog.show({
      controller: DialogController,
      templateUrl: 'dialog1.tmpl.html',
      parent: angular.element(document.body),
      targetEvent: ev,
      clickOutsideToClose:true
    })
  };

  //$scope.diamondImage = "/image/diamond-image?image=" + 3;//"/image/diamond-image?image=" + DiamondService.getCurrentDiamond().images[0].id;

  function DialogController(DiamondService, $scope, $mdDialog) {
      $scope.diamond = DiamondService.getCurrentDiamond();
      $scope.diamondImage = "/image/diamond-image?image=" + $scope.diamond.images[0].id;
      var number = 0;
      var pic = $scope.diamond.images;
      var len = pic.length;
      $scope.primitiveRotator = function(element){
          number++;
          number = number == len ? 0 : number;
          $scope.diamondImage  = "/image/diamond-image?image=" + $scope.diamond.images[number].id;
      }

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

}]);




