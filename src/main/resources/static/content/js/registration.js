angular
  .module('diamondApp', ['ngMaterial', 'ngAnimate', 'ngMessages'])
  .directive('valueMatches', ['$parse', function ($parse) {
      return {
        require: 'ngModel',
          link: function (scope, elm, attrs, ngModel) {
            var originalModel = $parse(attrs.valueMatches),
                secondModel = $parse(attrs.ngModel);
            // Watch for changes to this input
            scope.$watch(attrs.ngModel, function (newValue) {
              ngModel.$setValidity(attrs.name, newValue === originalModel(scope));
            });
            // Watch for changes to the value-matches model's value
            scope.$watch(attrs.valueMatches, function (newValue) {
              ngModel.$setValidity(attrs.name, newValue === secondModel(scope));
            });
          }
        };
      }])
  .controller('DemoCtrl', function($scope) {
    $scope.user = {
      email: '',
      phone: '',
      address: 'Mountain View, CA',
      description: 'Nuclear Missile Defense System',
      rate: 500,
      special: true
    };
  });