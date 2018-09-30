diamondApp.service('AlertService', function($http, $q, $mdDialog){
    var showAlert = function(message, title) {
        if(title==null){
            title = "Some error occurred during execution.";
        }
        $mdDialog.show(
            $mdDialog.alert()
                .parent(angular.element(document.querySelector('body')))
                .clickOutsideToClose(true)
                .title(title)
                .textContent(message)
                .ariaLabel('Alert')
                .ok('Got it!')
        );
    };
    return {
        showAlert: showAlert
    };
});


