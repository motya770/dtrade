diamondApp.controller('BalanceActivityController', function BalanceActivityController($scope, $http, $rootScope, BalanceActivityService) {
    var self = this;
    $scope.tab = 4;

    /*BalanceActivityService.getBalanceActivities().then(function (data) {
        self.balanceActivities = data;
    });*/


    $scope.getPreviousBalanceActivities = function (pageNumber) {
        $http.post('/balance-activity/by-account?pageNumber=' + (pageNumber + 1), null).then(function(response) {
            self.balanceActivities = response.data;
        });
    }

    $http.post('/balance-activity/by-account?pageNumber=0', null).then(function(response) {
        self.balanceActivities = response.data;
    });
});