diamondApp.controller('AccountController', ['$scope', "$http", "AccountService", function($scope, $http, AccountService) {
    var self = this;
    AccountService.currentAccount().then(function (user) {
        self.user = user;
    });
}]);