diamondApp.controller("ConfigController", function ConfigController($scope, $rootScope, $http, $cookies){
    var self = this;

    var url = new URL(window.location.href);
    var searchParams = new URLSearchParams(url.search);
    var assetName =  searchParams.get('config');  // outputs "m2-m3-m4-m5"

    if(assetName!=null && assetName != ""){
        $cookies.put("config", assetName);
    }

    $http.get("/config/get").then(function (response) {
        self.config = response.data;
    });
});
