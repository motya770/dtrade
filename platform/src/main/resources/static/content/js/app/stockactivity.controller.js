diamondApp.controller('StockActivityController', function StockActivityController($scope, $http, $rootScope) {
    var self = this;

    $http.post('/stock-activity/by-account', null).then(function(response) {
        self.stockActivities = response.data;
    });
});