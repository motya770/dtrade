diamondApp.service('AlertService', function($http, $q, $mdDialog){
    var showAlert = function(message, title, type) {
        if(title==null){
            title = "Some error occurred during execution.";
        }

        if(type!==undefined && type==='EMPTY_ACCOUNT') {
            $mdDialog.show(
                $mdDialog.alert()
                    .parent(angular.element(document.querySelector('sep-cab-page__top')))
                    .clickOutsideToClose(true)
                    .title(title)
                    .htmlContent('<div>You are not logged in. Please, <a href="/trade#!/login-form">login<div> or register.')
                    //.textContent(message)
                    .ariaLabel('Alert')
                    .ok('Got it!')
            );
        }else{
            $mdDialog.show(
                $mdDialog.alert()
                    .parent(angular.element(document.querySelector('sep-cab-page__top')))
                    .clickOutsideToClose(true)
                    .title(title)
                    .textContent(message)
                    .ariaLabel('Alert')
                    .ok('Got it!')
            );
        }
    };
    return {
        showAlert: showAlert
    };
});


