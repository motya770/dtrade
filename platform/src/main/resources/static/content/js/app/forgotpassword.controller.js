diamondApp.controller("ForgotPasswordController", function ForgotPasswordController($scope, $rootScope, $http, AlertService){

    var self = this;
    self.email = null;

    $scope.submit = function() {
        if (self.email) {
            $http.post('/accounts/forgot-password', self.email).then(function(response) {
                if(response.error){
                    AlertService.showAlert(response.error);
                }else {
                    AlertService.showAlert('The mail was sent to your email.', 'Password recovery');
                }
            });

        }
    };
});
