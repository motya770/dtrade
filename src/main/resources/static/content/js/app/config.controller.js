diamondApp.controller("ConfigController", function ConfigController($scope, $rootScope, $http){
    var self = this;
    $http.get("/config/get").then(function (response) {
        self.config = response.data;
    });
});
