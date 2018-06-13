diamondApp
  .config(function($mdIconProvider) {
      $mdIconProvider
         .iconSet('social', 'img/icons/sets/social-icons.svg', 24)
         .defaultIconSet('img/icons/sets/core-icons.svg', 24);
     })
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
  .controller('RegistrationController', function($scope, $http, AlertService) {
    $scope.account = {
      email: '',
     // phone: '',
      password: null,
    };

      $scope.createAccount = function (account) {

          console.log(account);

          account.pwd = account.password.confirm;
          $http.post("/accounts/register", account, null).then(function (response) {
            //console.log(response);
              if(response.data.error){
                  AlertService.showAlert(response.data.message);
                  return;
              }
              AlertService.showAlert("Your account was registered. Please, make login.", "Registered successfully");
          }, function (response) {
              if(response.data.error){
                  AlertService.showAlert(response.data.message);
                  return;
              }else {
                  AlertService.showAlert(response);
              }
          });
      };
  });