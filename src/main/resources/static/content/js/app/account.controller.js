diamondApp.controller("AccountController", function AccountController($scope, $rootScope, $http, AccountService){
    var self = this;
    //self.account = {};

    AccountService.currentAccount().then(function (account) {
        self.account = account;
    });
});
