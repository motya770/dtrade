diamondApp.controller('BalanceActivityController', function BalanceActivityController($scope, $http, $rootScope, BalanceActivityService) {
    var self = this;

    /*BalanceActivityService.getBalanceActivities().then(function (data) {
        self.balanceActivities = data;
    });*/

    $http.post('/balance-activity/by-account', null).then(function(response) {
        self.balanceActivities = response.data;
    });
});