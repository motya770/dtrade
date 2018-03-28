diamondApp.controller("DiamondController", function DiamondController($scope, $rootScope, $http, DiamondService){

    var self = this;

    DiamondService.getAllEnlistedDiamonds().then(function (data) {
        self.diamonds = data;
    })
});
