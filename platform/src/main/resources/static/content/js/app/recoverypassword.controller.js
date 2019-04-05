diamondApp.controller("RecoveryPasswordController", function RecoveryPasswordController($scope, $rootScope, $http, $location, AlertService){

    var self = this;
    self.pwd = null;
    self.recoveryGuid = $location.search()['recovery-guid'];
    self.pwdCheck = null;


    $scope.submit = function() {
        if (self.pwd===self.pwdCheck) {
            var recoveryObject = {pwd: self.pwd, recoveryGuid: self.recoveryGuid};
            $http.post('/accounts/recover-password', recoveryObject).then(function(response) {
                if(response.error){
                    AlertService.showAlert(response.error);
                }else {
                    AlertService.showAlert('You password was successfully changed.', 'Password changed');
                }
            });
        }
    };
});
