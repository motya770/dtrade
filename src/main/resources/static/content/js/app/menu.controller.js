diamondApp.controller("MenuController", function MenuController($scope, $location, AccountService) {

    /*
    $scope.getClass = function (path) {
        return ($location.path().substr(0, path.length) === path) ? 'active' : '';
    }*/

    $scope.isActive = function (viewLocation) {
        var active = (viewLocation === $location.path());
        return active;
    };

    var self = this;
    AccountService.currentAccountCall().then(function (account) {
        if(account.account=="empty"){
            self.account = null;
        }else {
            self.account = account;
        }
    });
});