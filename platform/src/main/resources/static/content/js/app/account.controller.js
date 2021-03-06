diamondApp.controller("AccountController", function AccountController($scope, $rootScope, $http, AccountService){
    var self = this;

    AccountService.currentAccountCall().then(function (account) {

        if(account.account=="empty"){
            self.account = null;
        }else {
            self.account = account;
        }

        $rootScope.$broadcast('accountReceived', account);
    });
});




