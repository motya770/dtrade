diamondApp.service('AlertService', function($http, $q, $mdDialog){
    var showAlert = function(message) {
        $mdDialog.show(
            $mdDialog.alert()
                .parent(angular.element(document.querySelector('body')))
                .clickOutsideToClose(true)
                .title("Some error occurred during execution.")
                .textContent(message)
                .ariaLabel('Alert')
                .ok('Got it!')
        );
    };
    return {
        showAlert: showAlert
    };
});


