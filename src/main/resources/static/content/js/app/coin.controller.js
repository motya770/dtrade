diamondApp.controller('CoinController', function CoinController($scope, $http) {

    var self = this;
    var diamondId = 9; 

    $http.post("/diamond/by-id?diamondId=" + diamondId, null, null).then(function(resp) {
        self.coin = resp.data;
        return resp.data;
    });

});