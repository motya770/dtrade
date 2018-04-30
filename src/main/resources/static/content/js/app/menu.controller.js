diamondApp.controller("MenuController", function MenuController($scope, $location, AccountService) {
    $scope.getClass = function (path) {
        return window.location.pathname === path ? 'active' : '';
    }

    var self = this;
    AccountService.currentAccount().then(function (account) {
        if(account.account=="empty"){
            self.account = null;
        }else {
            self.account = account;
        }
    });
});